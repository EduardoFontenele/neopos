package com.neopos.adapter.service;

import com.neopos.adapter.entity.ProductEntity;
import com.neopos.adapter.exception.BusinessLogicException;
import com.neopos.adapter.exception.ExceptionsTable;
import com.neopos.adapter.mapper.ProductMapper;
import com.neopos.adapter.repository.ProductRepository;
import com.neopos.application.core.domain.Product;
import com.neopos.application.ports.output.InsertProductOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InsertProductAdapter implements InsertProductOutputPort {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @Override
    public void insert(Product product, Map<String, String> capturedErrors) {
        if(!capturedErrors.isEmpty()) {
            Map<String, String> errors = new LinkedHashMap<>(capturedErrors);
            capturedErrors.clear();
            throw new BusinessLogicException(ExceptionsTable.FIELD_VALIDATION_ERROR, errors);
        }

        ProductEntity entity = productMapper.toEntity(product);
        productRepository.save(entity);
    }
}
