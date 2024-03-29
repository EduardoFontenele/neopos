package com.neopos.adapter.service;

import com.neopos.adapter.entity.ProductEntity;
import com.neopos.adapter.exception.BusinessLogicException;
import com.neopos.adapter.exception.ExceptionsTable;
import com.neopos.adapter.mapper.ProductMapper;
import com.neopos.adapter.repository.ProductRepository;
import com.neopos.adapter.utils.ProductFactories;
import com.neopos.application.core.domain.Product;
import com.neopos.application.ports.output.InsertProductOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class InsertProductAdapter implements InsertProductOutputPort {

    private final ProductRepository productRepository;
    private final ProductFactories productFactories;

    @Override
    public void insert(Product product, Map<String, String> capturedErrors) {
        if(!capturedErrors.isEmpty()) {
            Map<String, String> errors = new LinkedHashMap<>(capturedErrors);
            capturedErrors.clear();

            log.info("Exception found - one or more fields have validation errors");
            throw new BusinessLogicException(ExceptionsTable.FIELD_VALIDATION_ERROR, errors);
        }

        ProductEntity entity = productFactories.buildEntityObject(product);
        productRepository.save(entity);
    }
}
