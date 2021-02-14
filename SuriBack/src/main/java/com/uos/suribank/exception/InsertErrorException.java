package com.uos.suribank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InsertErrorException extends RuntimeException {
    public InsertErrorException(String message) {
        super(message);
    }
}
