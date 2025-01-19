package org.example.service.impl;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.BusinessException;
import org.example.exception.RepositoryException;
import org.example.exception.SystemException;
import org.example.model.ApplicationResponse;
import org.example.model.Fetch;
import org.example.model.database.Debtor;
import org.example.model.database.Debts;
import org.example.model.debts.CreateDebt;
import org.example.model.debts.UpdateDebt;
import org.example.model.mapper.DebtMapper;
import org.example.repository.DebtorRepository;
import org.example.repository.DebtsRepository;
import org.example.service.DebtsService;
import org.example.type.Codes;
import org.example.util.CompareUtils;
import org.example.util.StringUtils;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class DebtsServiceImpl implements DebtsService {

    @Autowired
    private DebtsRepository debtsRepository;

    @Autowired
    private DebtorRepository debtorRepository;

    @Autowired
    private DebtMapper debtMapper;

    @Override
    public ApplicationResponse fetchDebts() throws RepositoryException, BusinessException {
        List<Debts> debtorsList = findDebts();

        if (debtorsList.isEmpty()){
            throw new BusinessException(Codes.NOT_FOUND, "Dividas não encontradas");
        }

        Fetch fetch = new Fetch();
        fetch.setDebts(debtorsList);

        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setResponse(fetch);
        applicationResponse.setResponse_code(Codes.SUCCESS.getCode());
        applicationResponse.setResponse_message("Success");
        log.info("Success retrieved debts");
        return applicationResponse;
    }

    @Override
    public ApplicationResponse fetchDebtById(String id) throws RepositoryException, BusinessException {
        Debts debt = findDebt(StringUtils.isEmpty("id", id), "id_debt");
        Fetch fetch = new Fetch();
        fetch.setDebt(debt);
        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setResponse(fetch);
        applicationResponse.setResponse_code(Codes.SUCCESS.getCode());
        applicationResponse.setResponse_message("Success");
        log.info("Success retrieved debt {}", id);
        return applicationResponse;
    }

    @Override
    public ApplicationResponse fetchDebtsByDebtor(String id) throws RepositoryException, BusinessException {
        ApplicationResponse applicationResponse = new ApplicationResponse();
        Fetch fetch = new Fetch();
        List<Debts> debtsList = findDebts(id, "id");
        if (debtsList.isEmpty()){
            throw new BusinessException(Codes.NOT_FOUND, "Dividas não encontradas");
        }
        fetch.setDebts(debtsList);
        applicationResponse.setResponse(fetch);
        applicationResponse.setResponse_code(Codes.SUCCESS.getCode());
        applicationResponse.setResponse_message("Success");
        log.info("Success retrieved debtor {}", id);
        return applicationResponse;
    }

    @Override
    public ApplicationResponse create(CreateDebt debt) throws RepositoryException, BusinessException {
        ApplicationResponse applicationResponse = new ApplicationResponse();
        String id;

        Debts debtorEntity = debtMapper.toDebtor(debt);
        verifyDebt(debtorEntity);
        findDebtor(debt.getId_debtor());
        try {
            id = String.valueOf(debtsRepository.saveAndFlush(debtorEntity).getId_debt());
        } catch (PropertyValueException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            if (e.getCause() instanceof PropertyValueException propertyValueException) {
                log.error(String.format("%s, %s", propertyValueException, propertyValueException.getMessage()));
                throw new BusinessException(Codes.INVALID_PARAMETERS, String.format("Campo obrigatório %s não informado", propertyValueException.getPropertyName()));
            }
            log.error("Algo de errado aconteceu na criação da divida", e);
            throw new RepositoryException(Codes.INTERNAL_SERVER_ERROR, "Algo de errado aconteceu na criação da divida");
        }
        applicationResponse.setResponse_code(Codes.CREATE_SUCCESS.getCode());
        applicationResponse.setResponse_message("Success");
        log.info(String.format("Success created debt id= %s", id));
        return applicationResponse;
    }

    @Override
    public ApplicationResponse update(UpdateDebt debt) throws BusinessException, RepositoryException, SystemException {
        log.info(String.format("Updating debt %s from debtor %s", StringUtils.isEmpty("id_debt", debt.getId_debt()), StringUtils.isEmpty("id_debtor", debt.getId_debtor())));
        Debts oldDebtor = findDebt(debt.getId_debt(), "id_debt");
        Debts newDebtor = debtMapper.toDebtor(debt);

        Debts debtorEntity = CompareUtils.getUpdatedDebt(oldDebtor, newDebtor);
        verifyDebt(debtorEntity);
        try {
            debtsRepository.save(debtorEntity);
        } catch (Exception e){
            log.error("Algo de errado aconteceu na atualização da divida", e);
            throw new RepositoryException(Codes.INTERNAL_SERVER_ERROR, "Algo de errado aconteceu na atualização da divida");
        }
        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setResponse_code(Codes.SUCCESS.getCode());
        applicationResponse.setResponse_message("Success");
        log.info("Success updated debt {}", debt.getId_debt());
        return applicationResponse;
    }

    private static void verifyDebt(Debts debtorEntity) throws BusinessException {
        BigDecimal debt_missing = debtorEntity.getDebt().subtract(debtorEntity.getDebt_paid());
        if (debt_missing.compareTo(BigDecimal.ZERO) < 0){
            throw new BusinessException(Codes.INVALID_PARAMETERS, "Valor pago maior que o valor da divida");
        }
        debtorEntity.setDebt_missing(debt_missing);
    }

    @Override
    public ApplicationResponse delete(String id) throws BusinessException, RepositoryException {
        findDebts(id, "id");
        try {
            debtsRepository.deleteById(Long.valueOf(id));
        } catch (Exception e){
            log.error("Algo de errado aconteceu na exclusão da divida", e);
            throw new RepositoryException(Codes.INTERNAL_SERVER_ERROR, "Algo de errado aconteceu na exclusão da divida");
        }
        return null;
    }

    private List<Debts> findDebts() throws RepositoryException {
        try {
            return debtsRepository.findAll();
        } catch (Exception e){
            log.error("Algo de errado aconteceu na busca das dividas", e);
            throw new RepositoryException(Codes.INTERNAL_SERVER_ERROR, "Algo de errado aconteceu na busca das dividas");
        }
    }

    private Debts findDebt(String id, String searchType) throws RepositoryException, BusinessException {
        StringUtils.isEmpty(searchType, id);
        try {
            return debtsRepository.findById(Long.valueOf(id)).get();
        } catch (NoSuchElementException e){
            log.error("Divida não encontrada", e);
            throw new BusinessException(Codes.NOT_FOUND, "Divida não encontrada");
        } catch (Exception e){
            log.error("Algo de errado aconteceu na busca da divida", e);
            throw new RepositoryException(Codes.INTERNAL_SERVER_ERROR, "Algo de errado aconteceu na busca da divida");
        }
    }

    private List<Debts> findDebts(String id, String searchType) throws RepositoryException, BusinessException {
        StringUtils.isEmpty(searchType, id);
        try {
            return debtsRepository.findByIdDebtor(Integer.valueOf(id));
        } catch (NoSuchElementException e){
            log.error("Dividas não encontradas", e);
            throw new BusinessException(Codes.NOT_FOUND, "Dividas não encontradas");
        } catch (Exception e){
            log.error("Algo de errado aconteceu na busca do Devedor", e);
            throw new RepositoryException(Codes.INTERNAL_SERVER_ERROR, "Algo de errado aconteceu na busca das dividas");
        }
    }

    private Debtor findDebtor(String id) throws RepositoryException, BusinessException {
        StringUtils.isEmpty("id_debtor", id);
        try {
            return debtorRepository.findById(Long.valueOf(id)).get();
        } catch (NoSuchElementException e){
            log.error("Devedor não encontrado", e);
            throw new BusinessException(Codes.NOT_FOUND, "Devedor não encontrado");
        } catch (Exception e){
            log.error("Algo de errado aconteceu na busca do Devedor", e);
            throw new RepositoryException(Codes.INTERNAL_SERVER_ERROR, "Algo de errado aconteceu na busca do Devedor");
        }
    }

}
