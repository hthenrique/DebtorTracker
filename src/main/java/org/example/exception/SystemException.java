package org.example.exception;

import org.example.type.Codes;

public class SystemException extends BaseException{
    public SystemException(Codes responseCode, String message) {
        super(responseCode, message);
    }
}
