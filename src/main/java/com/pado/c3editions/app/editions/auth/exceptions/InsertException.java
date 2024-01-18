package com.pado.c3editions.app.editions.auth.exceptions;

import org.springframework.http.HttpStatus;


public class InsertException extends RuntimeException {
    private HttpStatus status=HttpStatus.EXPECTATION_FAILED;
    public InsertException() {
    }

    public InsertException(String message) {
        super(message);
    }

    public InsertException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsertException(Throwable cause) {
        super(cause);
    }

    public InsertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
