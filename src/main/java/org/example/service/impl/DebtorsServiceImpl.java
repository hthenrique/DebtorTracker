package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.BusinessException;
import org.example.exception.RepositoryException;
import org.example.exception.SystemException;
import org.example.model.ApplicationResponse;
import org.example.model.debtor.CreateDebtor;
import org.example.model.Fetch;
import org.example.model.debtor.UpdateDebtor;
import org.example.model.database.Debtor;
import org.example.model.mapper.DebtorMapper;
import org.example.model.type.ColumnTypes;
import org.example.repository.DebtorRepository;
import org.example.service.DebtorsService;
import org.example.type.Codes;
import org.example.util.CompareUtils;
import org.example.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class DebtorsServiceImpl implements DebtorsService {

    @Autowired
    private DebtorRepository debtorRepository;

    @Autowired
    private DebtorMapper debtorMapper;

    @Override
    public ApplicationResponse fetchUsers() throws RepositoryException, BusinessException {
        ApplicationResponse applicationResponse = new ApplicationResponse();
        List<Debtor> debtorsList = findDebtors();

        if (debtorsList.isEmpty()){
            throw new BusinessException(Codes.NOT_FOUND, "Devedores não encontrados");
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

    public ApplicationResponse fetchUserByAttribute(String column, String value) throws RepositoryException, BusinessException {
        ApplicationResponse applicationResponse = new ApplicationResponse();
        List<Debtor> debtors;

        column = ColumnTypes.fromColumnNick(column).getColumnName();

        switch (column) {
            case "id":
                debtors = findDebtorByColumn(value, "id");
                break;
            case "doc_number":
                debtors = findDebtorByColumn(value, "doc_number");
                break;
            case "name":
                debtors = findDebtorByColumn(value, "name");
                break;
            case "email":
                debtors = findDebtorByColumn(value, "email");
                break;
            default:
                throw new BusinessException(Codes.NOT_FOUND, "Não foi possível encontrar devedor com a coluna " + column);
        }

        Fetch fetch = new Fetch();
        fetch.setDebtors(debtors);
        applicationResponse.setResponse(fetch);
        applicationResponse.setResponse_code(Codes.SUCCESS.getCode());
        applicationResponse.setResponse_message("Success");
        log.info("Success retrieved debtor by {}={}", column, value);
        return applicationResponse;
    }

    @Override
    public ApplicationResponse create(CreateDebtor debtor) throws BusinessException, RepositoryException {
        ApplicationResponse applicationResponse = new ApplicationResponse();
        String id;
        if (findDebtor(debtor.getDoc_number(), "doc_number") != null){
            throw new BusinessException(Codes.USER_ALREADY_EXISTS, "Devedor já cadastrado");
        }
        Debtor debtorEntity = debtorMapper.toDebtor(debtor);
        try {
            id = String.valueOf(debtorRepository.saveAndFlush(debtorEntity).getId());
        } catch (Exception e){
            log.error("Algo de errado aconteceu na criação do Devedor", e);
            throw new RepositoryException(Codes.INTERNAL_SERVER_ERROR, "Algo de errado aconteceu na criação do Devedor");
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
            log.error("Algo de errado aconteceu na atualização do Devedor", e);
            throw new RepositoryException(Codes.INTERNAL_SERVER_ERROR, "Algo de errado aconteceu na atualização do Devedor");
        }
        applicationResponse.setResponse_code(Codes.SUCCESS.getCode());
        applicationResponse.setResponse_message("Success");
        log.info(String.format("Success updated debtor id=%s", debtor.getId()));
        return applicationResponse;
    }

    @Override
    public ApplicationResponse delete(String id) throws BusinessException, RepositoryException {
        log.info(String.format("Deleting debtor %s", StringUtils.isEmpty("id", id)));
        findDebtor(id, "id");
        try {
            debtorRepository.deleteById(Long.valueOf(id));
        } catch (Exception e){
            log.error("Algo de errado aconteceu na exclusão do Devedor", e);
            throw new RepositoryException(Codes.INTERNAL_SERVER_ERROR, "Algo de errado aconteceu na exclusão do Devedor");
        }
        return null;
    }

    private List<Debtor> findDebtors() throws RepositoryException {
        try {
            return debtorRepository.findAll();
        } catch (Exception e){
            log.error("Algo de errado aconteceu na busca dos Devedores", e);
            throw new RepositoryException(Codes.INTERNAL_SERVER_ERROR, "Algo de errado aconteceu na busca dos Devedores");
        }
    }

    private Debtor findDebtor(String id, String searchType) throws RepositoryException, BusinessException {
        StringUtils.isEmpty(searchType, id);
        try {
            if (searchType.equals("id")){
                return debtorRepository.findById(Long.valueOf(id)).get();
            }else {
                return debtorRepository.findByDocNumber(id);
            }
        } catch (NoSuchElementException e){
            log.error("Devedor não encontrado", e);
            throw new BusinessException(Codes.NOT_FOUND, "Devedor não encontrado");
        } catch (Exception e){
            log.error("Algo de errado aconteceu na busca do Devedor", e);
            throw new RepositoryException(Codes.INTERNAL_SERVER_ERROR, "Algo de errado aconteceu na busca do Devedor");
        }
    }

    private List<Debtor> findDebtorByColumn(String value, String column) throws RepositoryException, BusinessException {
        try {
            switch (column) {
                case "id":
                    return Collections.singletonList(debtorRepository.findById(Long.valueOf(value)).orElseThrow(() ->
                            new BusinessException(Codes.NOT_FOUND, "Devedor não encontrado")));
                case "doc_number":
                    return debtorRepository.findByDocNumberContaining(value);
                case "name":
                    return debtorRepository.findByNameContainingIgnoreCase(value);
                case "email":
                    return debtorRepository.findByEmailContainingIgnoreCase(value);
                default:
                    throw new BusinessException(Codes.INVALID_PARAMETERS, "Coluna de busca inválida");
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Erro ao buscar devedor por coluna", e);
            throw new RepositoryException(Codes.INTERNAL_SERVER_ERROR, "Erro ao buscar devedor");
        }
    }


}
