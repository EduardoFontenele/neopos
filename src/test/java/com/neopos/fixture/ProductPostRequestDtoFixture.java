package com.neopos.fixture;

import com.neopos.adapters.dto.request.ProductPostRequestDto;

import java.math.BigDecimal;

public class ProductPostRequestDtoFixture {
    public static ProductPostRequestDto createDefault() {
        ProductPostRequestDto productDto = new ProductPostRequestDto();
        productDto.setName("Default Product");
        productDto.setDescription("Default Product Description");
        productDto.setPrice(BigDecimal.valueOf(99.99));
        return productDto;
    }

    public static ProductPostRequestDto createCustom(String name, String description, BigDecimal price) {
        ProductPostRequestDto productDto = new ProductPostRequestDto();
        productDto.setName(name);
        productDto.setDescription(description);
        productDto.setPrice(price);
        return productDto;
    }
}
