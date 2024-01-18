package com.pado.c3editions.app.editions.auth.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {
    private HttpStatus status=HttpStatus.NO_CONTENT;
    public NotFoundException(String message) {
        super(message);
    }
}
