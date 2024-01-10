package com.neopos.adapters.service;

import com.neopos.adapters.entity.ProductEntity;
import com.neopos.adapters.mapper.ProductMapper;
import com.neopos.adapters.repository.ProductRepository;
import com.neopos.application.core.domain.Product;
import com.neopos.application.ports.output.FindProductsOutputPort;
import com.neopos.application.ports.output.InsertProductOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsertProductAdapter implements InsertProductOutputPort {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @Override
    public void insertProduct(Product product) {
        ProductEntity entity = productMapper.toEntity(product);
        productRepository.save(entity);
    }
}
