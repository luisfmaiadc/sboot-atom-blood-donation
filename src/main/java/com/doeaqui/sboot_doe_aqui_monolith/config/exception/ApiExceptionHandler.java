package com.doeaqui.sboot_doe_aqui_monolith.config.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import com.doeaqui.sboot_doe_aqui_monolith.model.StandardError;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> genericException(Exception e) {
        log.error(e.getMessage(), e);
        String errorMessage = e.getMessage() == null ? "Ocorreu um erro inesperado no servidor." : e.getMessage();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError err = standardErrorBuilder(httpStatus, errorMessage);
        return ResponseEntity.status(httpStatus).body(err);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<StandardError> jwtVerificationException(JWTVerificationException e) {
        log.error(e.getMessage(), e);
        String errorMessage =  "Token JWT inválido ou expirado.";
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        StandardError err = standardErrorBuilder(httpStatus, errorMessage);
        return ResponseEntity.status(httpStatus).body(err);
    }

    @ExceptionHandler({BadCredentialsException.class, AuthorizationDeniedException.class})
    public ResponseEntity<StandardError> unauthorizedException(Exception e) {
        log.error(e.getMessage(), e);
        String errorMessage;
        if (e instanceof BadCredentialsException) errorMessage = "Usuário inexistente ou senha incorreta.";
        else errorMessage = "Acesso negado.";
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        StandardError err = standardErrorBuilder(httpStatus, errorMessage);
        return ResponseEntity.status(httpStatus).body(err);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        String errorMessage = e.getMessage();
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        StandardError err = standardErrorBuilder(httpStatus, errorMessage);
        return ResponseEntity.status(httpStatus).body(err);
    }

    @ExceptionHandler({IllegalArgumentException.class, TypeMismatchException.class,
            MethodArgumentNotValidException.class, UnableToExecuteStatementException.class})
    public ResponseEntity<StandardError> badRequestException(Exception e) {
        log.error(e.getMessage(), e);
        String errorMessage;
        if (e instanceof TypeMismatchException || e instanceof MethodArgumentNotValidException) errorMessage = "Dados informados na requisição não são suportados.";
        else if (e instanceof UnableToExecuteStatementException) errorMessage = "Violação de integridade dos dados. Verifique se os dados informados já existem ou são válidos.";
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