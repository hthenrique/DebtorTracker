package org.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.model.ApplicationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
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
        log.error(String.format(applicationResponse.toString()));
        return ResponseEntity.status(e.getResponseCode().getHttpStatus()).body(applicationResponse);
    }
}
