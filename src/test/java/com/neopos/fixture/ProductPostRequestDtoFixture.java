package com.neopos.fixture;

import com.neopos.adapter.dto.request.ProductRequestDto;

import java.math.BigDecimal;

public class ProductPostRequestDtoFixture {
    public static ProductRequestDto gimmeSingleProductPostDto() {
        ProductRequestDto productDto = new ProductRequestDto();
        productDto.setName("Example Product");
        productDto.setDescription("This is a sample product.");
        productDto.setPrice(new BigDecimal("19.99"));
        return productDto;
    }

    public static ProductRequestDto createCustom(String name, String description, BigDecimal price) {
        ProductRequestDto productDto = new ProductRequestDto();
        productDto.setName(name);
        productDto.setDescription(description);
        productDto.setPrice(price);
        return productDto;
    }
}
