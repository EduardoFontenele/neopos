package com.neopos.adapter.service;

import com.neopos.adapter.entity.ProductEntity;
import com.neopos.adapter.exception.BusinessLogicException;
import com.neopos.adapter.exception.ExceptionsTable;
import com.neopos.adapter.mapper.ProductMapper;
import com.neopos.adapter.repository.ProductRepository;
import com.neopos.application.core.domain.Product;
import com.neopos.application.ports.output.FindProductByIdOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FindProductByIdAdapter implements FindProductByIdOutputPort {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Product findById(String id, Map<String, String> capturedErrors) {
        if(!capturedErrors.isEmpty()) {
            Map<String, String> errors = new LinkedHashMap<>(capturedErrors);
            capturedErrors.clear();
            throw new BusinessLogicException(ExceptionsTable.FIELD_VALIDATION_ERROR, errors);
        }

        ProductEntity foundProduct = productRepository.findById(id).orElseThrow(() -> {
            Map<String, String> errors = new LinkedHashMap<>();
            errors.put("product", "product with id " + id + " not found");
            return new BusinessLogicException(ExceptionsTable.RESOURCE_NOT_FOUND, errors);
        });

        return productMapper.toDomain(foundProduct);
    }
}
