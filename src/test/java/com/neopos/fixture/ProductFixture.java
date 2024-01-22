package com.neopos.fixture;

import com.neopos.adapter.dto.response.ProductGetDto;
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
    public static ProductGetDto gimmeSingleProductGetDto() {
        ProductGetDto productGetDto = new ProductGetDto();
        productGetDto.setId("1");
        productGetDto.setName("Example Product");
        productGetDto.setPrice(new BigDecimal("19.99"));
        productGetDto.setDescription("This is a sample product.");
        productGetDto.setLinks(new HashMap<>());
        return productGetDto;
    }

    public static List<ProductGetDto> gimmeProductGetDtoList() {
        List<ProductGetDto> productGetDtoList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ProductGetDto productGetDto = new ProductGetDto();
            productGetDto.setId(String.valueOf(i + 1));
            productGetDto.setName("Product " + (i + 1));
            productGetDto.setPrice(new BigDecimal("29.99"));
            productGetDto.setDescription("Description for Product " + (i + 1));
            productGetDto.setLinks(LinksFixture.gimmeSinglePageWithThreeProductsLink());
            productGetDtoList.add(productGetDto);
        }

        return productGetDtoList;
    }

}
