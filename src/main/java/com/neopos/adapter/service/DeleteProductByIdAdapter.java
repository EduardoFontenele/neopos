package com.neopos.adapter.service;

import com.neopos.adapter.exception.BusinessLogicException;
import com.neopos.adapter.exception.ExceptionsTable;
import com.neopos.adapter.repository.ProductRepository;
import com.neopos.application.ports.output.DeleteProductByIdOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class DeleteProductByIdAdapter implements DeleteProductByIdOutputPort {

    private final ProductRepository repository;

    @Override
    public void execute(String id, Map<String, String> capturedErrors) {
        if(!capturedErrors.isEmpty()) {
            Map<String, String> errors = new LinkedHashMap<>(capturedErrors);
            capturedErrors.clear();

            log.info("Exception found - ID with value {} is invalid", id);
            throw new BusinessLogicException(ExceptionsTable.FIELD_VALIDATION_ERROR, errors);
        }

        if(repository.findById(id).isEmpty()) {
            Map<String, String> errors = new LinkedHashMap<>();
            errors.put("id", String.format("Product with ID %s is not present", id));

            log.info("Exception found - product ID with value {} is not present", id);
            throw new BusinessLogicException(ExceptionsTable.RESOURCE_NOT_FOUND, errors);
        }

        repository.deleteById(id);
    }
}
