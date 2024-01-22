package com.neopos.config;

import com.neopos.adapter.service.CountProductsAdapter;
import com.neopos.adapter.service.FindProductByIdAdapter;
import com.neopos.adapter.service.FindProductsAdapter;
import com.neopos.adapter.service.InsertProductAdapter;
import com.neopos.application.core.usecase.FindProductByIdUseCase;
import com.neopos.application.core.usecase.FindProductsUseCase;
import com.neopos.application.core.usecase.InsertProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductBeansConfiguration {

    @Bean
    public InsertProductUseCase insertProductUseCase(InsertProductAdapter insertProductOutputPort) {
        return new InsertProductUseCase(insertProductOutputPort);
    }

    @Bean
    public FindProductsUseCase findProductUseCase(FindProductsAdapter findProductsOutputPort, CountProductsAdapter countProductsOutputPort) {
        return new FindProductsUseCase(findProductsOutputPort, countProductsOutputPort);
    }

    @Bean
    public FindProductByIdUseCase findProductByIdUseCase(FindProductByIdAdapter findProductByIdAdapter) {
        return new FindProductByIdUseCase(findProductByIdAdapter);
    }
}
