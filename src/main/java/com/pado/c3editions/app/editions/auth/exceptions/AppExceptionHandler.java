package com.pado.c3editions.app.editions.auth.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {



    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> handleUserException(UserException ex, HttpServletRequest request){
        return new ResponseEntity<Object>(AppError.builder().
                status(ex.getStatus()).
                timestamp(LocalDateTime.now()).
                message(ex.getMessage()).
                path(request.getContextPath()).
                build(),ex.getStatus());
    }

        @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex, HttpServletRequest request){
        return new ResponseEntity<Object>(AppError.builder().
                status(ex.getStatus()).
                timestamp(LocalDateTime.now()).
                message(ex.getMessage()).
                path(request.getContextPath()).
                build(),ex.getStatus());
    }

@ExceptionHandler(LoginException.class)
    public ResponseEntity<Object> loginHandlerException(LoginException ex, HttpServletRequest request){
        return new ResponseEntity<Object>(AppError.builder().
                status(HttpStatus.PRECONDITION_FAILED).
                timestamp(LocalDateTime.now()).
                message(ex.getMessage()).
                path(request.getContextPath()).
                build(),HttpStatus.PRECONDITION_FAILED);
    }
@ExceptionHandler(DuplicateException.class)
    public ResponseEntity<Object> inputHandlerException(DuplicateException ex, HttpServletRequest request){
        return new ResponseEntity<Object>(AppError.builder().
                status(HttpStatus.CONFLICT).
                timestamp(LocalDateTime.now()).
                message(ex.getMessage()).
                path(request.getContextPath()).
                build(),HttpStatus.CONFLICT);
    }
@ExceptionHandler(InsertException.class)
    public ResponseEntity<Object> inputHandlerException(InsertException ex, HttpServletRequest request){
        return new ResponseEntity<Object>(AppError.builder().
                status(HttpStatus.EXPECTATION_FAILED).
                timestamp(LocalDateTime.now()).
                message(ex.getMessage()).
                path(request.getContextPath()).
                error(ex.getCause()).
                build(),HttpStatus.EXPECTATION_FAILED);
    }
@ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> inputHandlerException(NotFoundException ex, HttpServletRequest request){
        return new ResponseEntity<Object>(AppError.builder().
                status(HttpStatus.NO_CONTENT).
                timestamp(LocalDateTime.now()).
                message(ex.getMessage()).
                path(request.getContextPath()).
                build(),HttpStatus.NO_CONTENT);
    }

}
