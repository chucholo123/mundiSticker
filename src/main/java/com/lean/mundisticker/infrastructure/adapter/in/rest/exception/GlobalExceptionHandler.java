package com.lean.mundisticker.infrastructure.adapter.in.rest.exception;

import com.lean.mundisticker.domain.exception.DomainException;
import com.lean.mundisticker.domain.exception.EntidadNoEncontradaException;
import com.lean.mundisticker.domain.exception.ReglaNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntidadNoEncontradaException.class)
    public ResponseEntity<ErrorResponse> handleEntidadNoEncontrada(EntidadNoEncontradaException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Entidad No Encontrada",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<ErrorResponse> handleReglaNegocio(ReglaNegocioException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflicto de Regla de Negocio",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error de Dominio",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    return fieldName + ": " + errorMessage;
                })
                .collect(Collectors.toList());

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error de Validación",
                "Los datos de entrada no son válidos",
                details
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error Interno del Servidor",
                "Ha ocurrido un error inesperado. Por favor, contacte al soporte.",
                List.of(ex.getMessage()) // En producción, es mejor no exponer detalles internos
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
