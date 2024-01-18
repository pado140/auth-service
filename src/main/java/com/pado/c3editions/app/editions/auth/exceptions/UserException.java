package com.pado.c3editions.app.editions.auth.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends RuntimeException{
    private HttpStatus status;

    public UserException(HttpStatus status) {
        this.status = status;
    }

    public UserException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public UserException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public UserException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }

    public UserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }
}
