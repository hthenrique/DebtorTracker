package org.example.exception;

import org.example.type.Codes;

public class RepositoryException extends BaseException{
    public RepositoryException(Codes code, String message) {
        super(code, message);
    }
}
