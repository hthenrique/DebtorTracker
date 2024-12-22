package org.example.service;

import org.example.exception.BusinessException;
import org.example.exception.RepositoryException;
import org.example.exception.SystemException;
import org.example.model.ApplicationResponse;
import org.example.model.debts.CreateDebt;
import org.example.model.debts.UpdateDebt;

public interface DebtsService {
    ApplicationResponse fetchDebts() throws BusinessException, RepositoryException;
    ApplicationResponse fetchDebtsByDebtor(String id) throws RepositoryException, BusinessException;
    ApplicationResponse create(CreateDebt debtor) throws BusinessException, RepositoryException;
    ApplicationResponse update(UpdateDebt debtor) throws BusinessException, RepositoryException, SystemException;
    ApplicationResponse delete(String id) throws BusinessException, RepositoryException;
}
