package org.example.type;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Codes {

    SUCCESS("200",HttpStatus.OK),
    CREATE_SUCCESS("201", HttpStatus.CREATED),
    INVALID_PARAMETERS("400", HttpStatus.BAD_REQUEST),
    NOT_FOUND("404", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS("409", HttpStatus.CONFLICT),
    INTERNAL_SERVER_ERROR("500", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final HttpStatus httpStatus;

    Codes(String code, HttpStatus httpStatus){
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
