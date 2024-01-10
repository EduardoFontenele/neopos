package com.neopos.adapters.service;

import com.neopos.adapters.entity.ProductEntity;
import com.neopos.adapters.mapper.ProductMapper;
import com.neopos.adapters.repository.ProductRepository;
import com.neopos.application.core.domain.Product;
import com.neopos.application.ports.output.FindProductsOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindProductsAdapter implements FindProductsOutputPort {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @Override
    public List<Product> listProducts() {
        List<ProductEntity> productEntities = productRepository.findAll();
        return productEntities.stream().map(productMapper::toDomain).toList();
    }
}
