package com.neopos.adapters.service;

import com.neopos.adapters.entity.ProductEntity;
import com.neopos.adapters.mapper.ProductMapper;
import com.neopos.adapters.repository.ProductRepository;
import com.neopos.application.core.domain.Product;
import com.neopos.application.ports.output.FindProductsOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
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
