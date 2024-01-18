package com.pado.c3editions.app.editions.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;

public class LoginException extends BadCredentialsException {
    private HttpStatus status=HttpStatus.PRECONDITION_FAILED;
    public LoginException(String msg) {
        super(msg);
    }
}
