package com.doeaqui.sboot_atom_blood_donation.config.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.doeaqui.sboot_atom_blood_donation.model.StandardError;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> genericException(Exception e) {
        String errorMessage = e.getMessage() == null ? "Ocorreu um erro inesperado no servidor." : e.getMessage();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError err = standardErrorBuilder(httpStatus, errorMessage);
        return ResponseEntity.status(httpStatus).body(err);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<StandardError> jwtVerificationException(JWTVerificationException e) {
        String errorMessage =  "Token JWT inválido ou expirado.";
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        StandardError err = standardErrorBuilder(httpStatus, errorMessage);
        return ResponseEntity.status(httpStatus).body(err);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StandardError> badCredentialsException(BadCredentialsException e) {
        String errorMessage =  "Usuário inexistente ou senha incorreta.";
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        StandardError err = standardErrorBuilder(httpStatus, errorMessage);
        return ResponseEntity.status(httpStatus).body(err);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFoundException(ResourceNotFoundException e) {
        String errorMessage = e.getMessage();
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        StandardError err = standardErrorBuilder(httpStatus, errorMessage);
        return ResponseEntity.status(httpStatus).body(err);
    }

    @ExceptionHandler({IllegalArgumentException.class, TypeMismatchException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<StandardError> badRequestException(Exception e) {
        String errorMessage;
        if (e instanceof TypeMismatchException || e instanceof MethodArgumentNotValidException) errorMessage = "Dados informados na requisição não são suportados.";
        else errorMessage = e.getMessage();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        StandardError err = standardErrorBuilder(httpStatus, errorMessage);
        return ResponseEntity.status(httpStatus).body(err);
    }

    private StandardError standardErrorBuilder(HttpStatus status, String errorMessage) {
        return new StandardError(
                OffsetDateTime.now(),
                status.value(),
                errorMessage
        );
    }
}