package org.example.exception;

import lombok.Getter;
import org.example.type.Codes;

@Getter
public class BaseException extends Exception{
    private final Codes responseCode;
    private final String message;

    public BaseException(Codes responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }

}
