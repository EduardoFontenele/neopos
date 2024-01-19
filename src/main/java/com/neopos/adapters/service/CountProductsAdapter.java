package com.neopos.adapters.service;

import com.neopos.adapters.repository.ProductRepository;
import com.neopos.application.ports.output.CountProductsOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CountProductsAdapter implements CountProductsOutputPort {

    private final ProductRepository productRepository;

    @Override
    public int count() {
        return (int) productRepository.count();
    }
}
