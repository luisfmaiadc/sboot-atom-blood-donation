package com.doeaqui.sboot_atom_blood_donation.config.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.doeaqui.sboot_atom_blood_donation.model.StandardError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> genericException(Exception e) {
        String errorMessage = e.getMessage() == null ? "Ocorreu um erro inesperado no servidor." : e.getMessage();
        StandardError err = new StandardError(
                OffsetDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                errorMessage
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<StandardError> jwtVerificationException(JWTVerificationException e) {
        String errorMessage =  "Token JWT inválido ou expirado.";
        StandardError err = new StandardError(
                OffsetDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                errorMessage
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
    }
}