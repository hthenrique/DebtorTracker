package org.example.exception;

import org.example.type.Codes;

public class BusinessException extends BaseException {
    public BusinessException(Codes code, String message) {
        super(code, message);
    }
}
