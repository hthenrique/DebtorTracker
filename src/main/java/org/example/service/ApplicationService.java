package org.example.service;

import org.example.exception.BusinessException;
import org.example.exception.RepositoryException;
import org.example.exception.SystemException;
import org.example.model.ApplicationResponse;
import org.example.model.CreateDebtor;
import org.example.model.UpdateDebtor;

public interface ApplicationService {
    ApplicationResponse fetchUsers() throws BusinessException, RepositoryException;
    ApplicationResponse fetchUser(String id) throws RepositoryException, BusinessException;
    ApplicationResponse create(CreateDebtor debtor) throws BusinessException, RepositoryException;
    ApplicationResponse update(UpdateDebtor debtor) throws BusinessException, RepositoryException, SystemException;
    ApplicationResponse delete(String id) throws BusinessException, RepositoryException;
}
