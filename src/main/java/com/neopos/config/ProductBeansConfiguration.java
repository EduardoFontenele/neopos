package com.neopos.config;

import com.neopos.adapters.service.FindProductsAdapter;
import com.neopos.adapters.service.InsertProductAdapter;
import com.neopos.application.core.usecase.ProductUseCases;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductBeansConfiguration {

    @Bean
    public ProductUseCases insertProductUseCase(InsertProductAdapter insertProductOutputPort, FindProductsAdapter findProductsAdapter) {
        return new ProductUseCases(insertProductOutputPort, findProductsAdapter);
    }
}
