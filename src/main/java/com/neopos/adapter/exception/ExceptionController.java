package com.neopos.adapter.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {


    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<ExceptionDto> handle(BusinessLogicException ex) {
        ExceptionDto response = new ExceptionDto();
        response.setErrorMessage(ex.getErrorMessage());
        response.setHttpStatus(ex.getHttpStatus());
        response.setFieldErrors(ex.getCapturedErrors());
        return ResponseEntity.status(response.getHttpStatus().value()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handle(MethodArgumentNotValidException ex) {
        ExceptionDto response = new ExceptionDto();
        Map<String, String> errors = new LinkedHashMap<>();

        response.setErrorMessage(ExceptionsTable.FIELD_VALIDATION_ERROR.getMessage());
        response.setHttpStatus(HttpStatus.BAD_REQUEST);

        for(FieldError fieldError : ex.getFieldErrors()) {
            String field = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            errors.put(field, errorMessage);
        }

        response.setFieldErrors(errors);

        return ResponseEntity.status(400).body(response);
    }
}
