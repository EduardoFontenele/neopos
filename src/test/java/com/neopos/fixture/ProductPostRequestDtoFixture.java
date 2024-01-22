package com.neopos.fixture;

import com.neopos.adapter.dto.request.ProductPostRequestDto;

import java.math.BigDecimal;

public class ProductPostRequestDtoFixture {
    public static ProductPostRequestDto gimmeSingleProductPostDto() {
        ProductPostRequestDto productDto = new ProductPostRequestDto();
        productDto.setName("Example Product");
        productDto.setDescription("This is a sample product.");
        productDto.setPrice(new BigDecimal("19.99"));
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
