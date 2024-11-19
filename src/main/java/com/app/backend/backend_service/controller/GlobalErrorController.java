package com.app.backend.backend_service.controller;

import com.app.backend.backend_service.exception.MessageBrokerException;
import com.app.backend.backend_service.exception.ResourceBadException;
import com.app.backend.backend_service.exception.ResourceNotfoundException;
import com.app.backend.backend_service.model.ApiResponse;
import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> internalServerError(Exception e){
        Sentry.captureException(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }

    @ExceptionHandler(MessageBrokerException.class)
    public ResponseEntity<ApiResponse> messageBrokerError(MessageBrokerException e){
        Sentry.captureException(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> internalServerErrorMissingRequestBody(HttpMessageNotReadableException e){
        Sentry.captureException(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Request body is missing !"));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse> usernamePasswordNotfoundException(UsernameNotFoundException e){
        Sentry.captureException(e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.failed(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> usernamePasswordBadCredentialsException(BadCredentialsException e){
        Sentry.captureException(e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.failed(HttpStatus.UNAUTHORIZED.value(), "Username or password is incorrect !"));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse> usernamePasswordBadCredentialsException(AuthenticationException e){
        Sentry.captureException(e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.failed(HttpStatus.UNAUTHORIZED.value(), "Username or password is incorrect !"));
    }

    @ExceptionHandler(ResourceBadException.class)
    public ResponseEntity<ApiResponse> badRequestException(ResourceBadException e){
        Sentry.captureException(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failed(HttpStatus.BAD_REQUEST.value(), e.getMessageError()));
    }

    @ExceptionHandler(ResourceNotfoundException.class)
    public ResponseEntity<ApiResponse> notfoundException(ResourceNotfoundException e){
        Sentry.captureException(e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failed(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.info("error request invalid method argument : {}",ex.getMessage());
        List<String> errorsMesssage = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorsMesssage.add(error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.failed(HttpStatus.BAD_REQUEST.value(), errorsMesssage));
    }
}
