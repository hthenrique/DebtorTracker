package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.BusinessException;
import org.example.exception.RepositoryException;
import org.example.exception.SystemException;
import org.example.model.ApplicationResponse;
import org.example.model.CreateDebtor;
import org.example.model.Fetch;
import org.example.model.UpdateDebtor;
import org.example.model.database.Debtor;
import org.example.model.mapper.DebtorMapper;
import org.example.repository.DebtorRepository;
import org.example.repository.DebtsRepository;
import org.example.service.ApplicationService;
import org.example.type.Codes;
import org.example.util.CompareUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private DebtorRepository debtorRepository;

    @Autowired
    private DebtsRepository debtsRepository;

    @Autowired
    private DebtorMapper debtorMapper;

    @Override
    public ApplicationResponse fetchUsers() throws RepositoryException, BusinessException {
        ApplicationResponse applicationResponse = new ApplicationResponse();
        List<Debtor> debtorsList = findDebtors();

        if (debtorsList.isEmpty()){
            throw new BusinessException(Codes.NOT_FOUND, "Usuários não encontrados");
        }

        Fetch fetch = new Fetch();
        fetch.setDebtors(debtorsList);

        applicationResponse.setResponse(fetch);
        applicationResponse.setResponse_code(Codes.SUCCESS.getCode());
        applicationResponse.setResponse_message("Success");
        log.info("Success retrieved debtors");
        return applicationResponse;
    }

    @Override
    public ApplicationResponse fetchUser(String id) throws RepositoryException, BusinessException {
        ApplicationResponse applicationResponse = new ApplicationResponse();
        Fetch fetch = new Fetch();
        fetch.setDebtor(findDebtor(id, "id"));
        applicationResponse.setResponse(fetch);
        applicationResponse.setResponse_code(Codes.SUCCESS.getCode());
        applicationResponse.setResponse_message("Success");
        log.info("Success retrieved debtor {}", id);
        return applicationResponse;
    }

    @Override
    public ApplicationResponse create(CreateDebtor debtor) throws BusinessException, RepositoryException {
        ApplicationResponse applicationResponse = new ApplicationResponse();
        String id;
        if (findDebtor(debtor.getDoc_number(), "doc_number") != null){
            throw new BusinessException(Codes.USER_ALREADY_EXISTS, "Usuário já cadastrado");
        }
        Debtor debtorEntity = debtorMapper.toDebtor(debtor);
        try {
            id = String.valueOf(debtorRepository.saveAndFlush(debtorEntity).getId());
        } catch (Exception e){
            log.error("Algo de errado aconteceu na criação do usuário", e);
            throw new RepositoryException(Codes.INTERNAL_SERVER_ERROR, "Algo de errado aconteceu na criação do usuário");
        }
        applicationResponse.setResponse_code(Codes.CREATE_SUCCESS.getCode());
        applicationResponse.setResponse_message("Success");
        log.info(String.format("Success created debtor id= %s, name= %s, docNumber= %s", id, debtorEntity.getName(), debtorEntity.getDocNumber()));
        return applicationResponse;
    }

    @Override
    public ApplicationResponse update(UpdateDebtor debtor) throws BusinessException, RepositoryException, SystemException {
        ApplicationResponse applicationResponse = new ApplicationResponse();
        Debtor oldDebtor = findDebtor(debtor.getId(), "id");
        Debtor newDebtor = debtorMapper.toDebtor(debtor);

        Debtor debtorEntity = CompareUtils.getUpdatedDebtor(oldDebtor, newDebtor);

        try {
            debtorRepository.save(debtorEntity);
        } catch (Exception e){
            log.error("Algo de errado aconteceu na atualização do usuário", e);
            throw new RepositoryException(Codes.INTERNAL_SERVER_ERROR, "Algo de errado aconteceu na atualização do usuário");
        }
        applicationResponse.setResponse_code(Codes.SUCCESS.getCode());
        applicationResponse.setResponse_message("Success");
        return applicationResponse;
    }

    @Override
    public ApplicationResponse delete(String id) throws BusinessException, RepositoryException {
        findDebtor(id, "id");
        try {
            debtorRepository.deleteById(Long.valueOf(id));
        } catch (Exception e){
            log.error("Algo de errado aconteceu na exclusão do usuário", e);
            throw new RepositoryException(Codes.INTERNAL_SERVER_ERROR, "Algo de errado aconteceu na exclusão do usuário");
        }
        return null;
    }

    private List<Debtor> findDebtors() throws RepositoryException {
        try {
            return debtorRepository.findAll();
        } catch (Exception e){
            log.error("Algo de errado aconteceu na busca dos usuários", e);
            throw new RepositoryException(Codes.INTERNAL_SERVER_ERROR, "Algo de errado aconteceu na busca dos usuários");
        }
    }

    private Debtor findDebtor(String id, String searchType) throws RepositoryException, BusinessException {
        try {
            if (searchType.equals("id")){
                return debtorRepository.findById(Long.valueOf(id)).get();
            }else {
                return debtorRepository.findByDocNumber(id);
            }
        } catch (NoSuchElementException e){
            log.error("Usuário não encontrado", e);
            throw new BusinessException(Codes.NOT_FOUND, "Usuário não encontrado");
        } catch (Exception e){
            log.error("Algo de errado aconteceu na busca do usuário", e);
            throw new RepositoryException(Codes.INTERNAL_SERVER_ERROR, "Algo de errado aconteceu na busca do usuário");
        }
    }


}
