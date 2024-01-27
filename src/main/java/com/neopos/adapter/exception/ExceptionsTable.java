package com.neopos.adapter.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionsTable {

    INVALID_PAGE_ARGUMENTS(HttpStatus.BAD_REQUEST, "Invalid operation. Page number or size is invalid"),
    INVALID_ID_VALUE(HttpStatus.BAD_REQUEST, "Invalid operation due to invalid product ID"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not found or doesn't exist"),
    FIELD_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Invalid operation. One or more fields have invalid values");

    private final HttpStatus httpStatus;
    private final String message;

    ExceptionsTable(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
