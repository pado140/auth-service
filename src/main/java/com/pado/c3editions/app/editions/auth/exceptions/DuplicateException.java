package com.pado.c3editions.app.editions.auth.exceptions;

import org.springframework.http.HttpStatus;


public class DuplicateException extends RuntimeException {
    private HttpStatus status=HttpStatus.CONFLICT;
    private Object data;
    public DuplicateException() {
    }

    public DuplicateException(String message) {
        super(message);
    }

    public DuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateException(Throwable cause) {
        super(cause);
    }

    public DuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
