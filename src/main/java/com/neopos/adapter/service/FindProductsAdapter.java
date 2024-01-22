package com.neopos.adapter.service;

import com.neopos.adapter.entity.ProductEntity;
import com.neopos.adapter.mapper.ProductMapper;
import com.neopos.adapter.repository.ProductRepository;
import com.neopos.application.core.domain.Product;
import com.neopos.application.ports.output.FindProductsOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindProductsAdapter implements FindProductsOutputPort {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;


    @Override
    public List<Product> findAll(int pageNumber, int pageSize) {
        Page<ProductEntity> productEntityPage;

        if(productRepository.count() > 0) {
            PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
            productEntityPage = productRepository.findAll(pageRequest);
            return productEntityPage.get().toList().stream().map(productMapper::toDomain).toList();
        } else {
            return new ArrayList<>();
        }
    }
}
