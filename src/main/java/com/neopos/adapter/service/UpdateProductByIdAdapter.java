package com.neopos.adapter.service;

import com.neopos.adapter.entity.ProductEntity;
import com.neopos.adapter.exception.BusinessLogicException;
import com.neopos.adapter.exception.ExceptionsTable;
import com.neopos.adapter.repository.ProductRepository;
import com.neopos.adapter.utils.ProductFactories;
import com.neopos.application.core.domain.Product;
import com.neopos.application.ports.output.UpdateProductByIdOutputPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Log4j2
public class UpdateProductByIdAdapter implements UpdateProductByIdOutputPort {

    private final ProductRepository productRepository;
    private final ProductFactories productFactories;

    @Override
    @Transactional
    public Product execute(Product product, Map<String, String> capturedErrors, String id) {
        if(!capturedErrors.isEmpty()) {
            Map<String, String> errors = new LinkedHashMap<>(capturedErrors);
            capturedErrors.clear();

            log.info("Exception found - one or more fields have validation errors");
            throw new BusinessLogicException(ExceptionsTable.FIELD_VALIDATION_ERROR, errors);
        }

        if(!productRepository.existsById(id)) {
            Map<String, String> errors = new LinkedHashMap<>();
            errors.put("id", String.format("Product with ID %s is not present", id));

            log.info("Exception found - product ID with value {} is not present", id);
            throw new BusinessLogicException(ExceptionsTable.RESOURCE_NOT_FOUND, errors);
        }

        AtomicReference<ProductEntity> atomicRef = new AtomicReference<>();

        productRepository.findById(id).ifPresent(productEntity -> {
            if(product.getName() != null) {
                productEntity.setName(product.getName());
            }

            if(product.getDescription() != null) {
                productEntity.setDescription(product.getDescription());
            }

            if(product.getPrice() != null){
                productEntity.setPrice(product.getPrice());
            }

            atomicRef.set(productRepository.save(productEntity));
        });

        return productFactories.buildDomainObject(atomicRef.get());
    }
}
