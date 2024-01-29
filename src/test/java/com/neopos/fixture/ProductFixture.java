package com.neopos.fixture;

import com.neopos.adapter.dto.response.ProductResponseDto;
import com.neopos.application.core.domain.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductFixture {

    public static Product gimmeSingleProduct() {
        Product product = new Product();
        product.setId("1");
        product.setName("Example Product");
        product.setDescription("This is a sample product.");
        product.setPrice(new BigDecimal("19.99"));
        return product;
    }

    public static List<Product> gimmeProductList() {
        List<Product> productList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Product product = new Product();
            product.setId(String.valueOf(i + 1));
            product.setName("Product " + (i + 1));
            product.setDescription("Description for Product " + (i + 1));
            product.setPrice(new BigDecimal("29.99"));
            productList.add(product);
        }

        return productList;
    }
    public static ProductResponseDto gimmeSingleProductGetDto() {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setId("1");
        productResponseDto.setName("Example Product");
        productResponseDto.setPrice("19.99");
        productResponseDto.setDescription("This is a sample product.");
        productResponseDto.setLinks(new HashMap<>());
        return productResponseDto;
    }

    public static List<ProductResponseDto> gimmeProductGetDtoList() {
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ProductResponseDto productResponseDto = new ProductResponseDto();
            productResponseDto.setId(String.valueOf(i + 1));
            productResponseDto.setName("Product " + (i + 1));
            productResponseDto.setPrice("29.99");
            productResponseDto.setDescription("Description for Product " + (i + 1));
            productResponseDto.setLinks(LinksFixture.gimmeSinglePageWithThreeProductsLink());
            productResponseDtoList.add(productResponseDto);
        }

        return productResponseDtoList;
    }

}
