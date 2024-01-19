package com.neopos.adapters.service;

import com.neopos.adapters.entity.ProductEntity;
import com.neopos.adapters.mapper.ProductMapper;
import com.neopos.adapters.repository.ProductRepository;
import com.neopos.application.core.domain.Product;
import com.neopos.application.ports.output.FindProductByIdOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindProductByIdAdapter implements FindProductByIdOutputPort {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @Override
    public Product findById(String id) {
        ProductEntity foundProduct = productRepository.findById(id).get();
        return productMapper.toDomain(foundProduct);
    }
}
