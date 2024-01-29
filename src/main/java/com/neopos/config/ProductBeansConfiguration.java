package com.neopos.config;

import com.neopos.adapter.service.*;
import com.neopos.application.core.usecase.*;
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

    @Bean
    public DeleteProductByIdUseCase deleteProductByIdUseCase(DeleteProductByIdAdapter deleteProductByIdAdapter) {
        return new DeleteProductByIdUseCase(deleteProductByIdAdapter);
    }

    @Bean
    public UpdateProductByIdUseCase updateProductByIdUseCase(UpdateProductByIdAdapter updateProductByIdAdapter) {
        return new UpdateProductByIdUseCase(updateProductByIdAdapter);
    }
}
