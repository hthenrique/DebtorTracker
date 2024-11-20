package org.example.exception;

import org.example.model.ApplicationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler({RepositoryException.class, BusinessException.class, SystemException.class})
    public ResponseEntity<?> handleException(Exception ex) {
        return handleRepositoryException((BaseException) ex);
    }

    private ResponseEntity<ApplicationResponse> handleRepositoryException(BaseException e) {
        ApplicationResponse applicationResponse = new ApplicationResponse();
        applicationResponse.setResponse_code(e.getResponseCode().getCode());
        applicationResponse.setResponse_message(e.getMessage());
        return ResponseEntity.status(e.getResponseCode().getHttpStatus()).body(applicationResponse);
    }
}
