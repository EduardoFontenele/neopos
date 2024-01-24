package com.neopos.adapter.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BusinessLogicException extends RuntimeException {
    private String errorMessage;
    private HttpStatus httpStatus;
    private Map<String, String> capturedErrors;

    public BusinessLogicException(ExceptionsTable exceptionsTable, Map<String, String> capturedErrors) {
        super();
        this.errorMessage = exceptionsTable.getMessage();
        this.httpStatus = exceptionsTable.getHttpStatus();
        this.capturedErrors = capturedErrors;
    }
}
