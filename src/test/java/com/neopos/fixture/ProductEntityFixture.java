package com.neopos.fixture;

import com.neopos.adapter.entity.ProductEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductEntityFixture {
    public static ProductEntity gimmeSingleProductEntity() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId("1");
        productEntity.setName("Example Product");
        productEntity.setDescription("This is a sample product.");
        productEntity.setPrice(new BigDecimal("19.99"));
        return productEntity;
    }

    public static List<ProductEntity> gimmeProductEntityList() {
        List<ProductEntity> productEntityList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setId(String.valueOf(i + 1));
            productEntity.setName("Product " + (i + 1));
            productEntity.setDescription("Description for Product " + (i + 1));
            productEntity.setPrice(new BigDecimal("29.99"));
            productEntityList.add(productEntity);
        }

        return productEntityList;
    }
}
