package com.neopos.adapter.mapper;

import com.neopos.adapter.dto.request.ProductRequestDto;
import com.neopos.adapter.dto.response.ProductResponseDto;
import com.neopos.adapter.entity.ProductEntity;
import com.neopos.application.core.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest {
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @Test
    @DisplayName("Mapping Product to ProductEntity")
    void mapProductToProductEntity() {
        // Arrange
        Product product = new Product();
        product.setId("1");
        product.setName("Example Product");
        product.setDescription("This is a sample product.");
        product.setPrice(new BigDecimal("19.99"));

        // Act
        ProductEntity productEntity = productMapper.toEntity(product);

        // Assert
        assertThat(productEntity).isNotNull();
        assertThat(productEntity.getId()).isEqualTo(product.getId());
        assertThat(productEntity.getName()).isEqualTo(product.getName());
        assertThat(productEntity.getDescription()).isEqualTo(product.getDescription());
        assertThat(productEntity.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    @DisplayName("Mapping ProductPostRequestDto to Product")
    void mapProductPostRequestDtoToProduct() {
        // Arrange
        ProductRequestDto dto = new ProductRequestDto();
        dto.setName("Example Product");
        dto.setDescription("This is a sample product.");
        dto.setPrice(new BigDecimal("19.99"));

        // Act
        Product product = productMapper.toDomain(dto);

        // Assert
        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo(dto.getName());
        assertThat(product.getDescription()).isEqualTo(dto.getDescription());
        assertThat(product.getPrice()).isEqualTo(dto.getPrice());
    }

    @Test
    @DisplayName("Mapping ProductEntity to Product")
    void mapProductEntityToProduct() {
        // Arrange
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId("1");
        productEntity.setName("Example Product");
        productEntity.setDescription("This is a sample product.");
        productEntity.setPrice(new BigDecimal("19.99"));

        // Act
        Product product = productMapper.toDomain(productEntity);

        // Assert
        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo(productEntity.getId());
        assertThat(product.getName()).isEqualTo(productEntity.getName());
        assertThat(product.getDescription()).isEqualTo(productEntity.getDescription());
        assertThat(product.getPrice()).isEqualTo(productEntity.getPrice());
    }

    @Test
    @DisplayName("Mapping Product to ProductGetDto")
    void mapProductToProductGetDto() {
        // Arrange
        Product product = new Product();
        product.setId("1");
        product.setName("Example Product");
        product.setDescription("This is a sample product.");
        product.setPrice(new BigDecimal("19.99"));

        // Act
        ProductResponseDto productResponseDto = productMapper.toGetDto(product);

        // Assert
        assertThat(productResponseDto).isNotNull();
        assertThat(productResponseDto.getId()).isEqualTo(product.getId());
        assertThat(productResponseDto.getName()).isEqualTo(product.getName());
        assertThat(productResponseDto.getDescription()).isEqualTo(product.getDescription());
        assertThat(productResponseDto.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    @DisplayName("Mapping List<Product> to List<ProductGetDto>")
    void mapProductListToProductGetDtoList() {
        // Arrange
        Product product1 = new Product();
        product1.setId("1");
        product1.setName("Product 1");
        product1.setDescription("Description 1");
        product1.setPrice(new BigDecimal("19.99"));

        Product product2 = new Product();
        product2.setId("2");
        product2.setName("Product 2");
        product2.setDescription("Description 2");
        product2.setPrice(new BigDecimal("29.99"));

        List<Product> productList = Arrays.asList(product1, product2);

        // Act
        List<ProductResponseDto> productResponseDtoList = productMapper.toGetDtoList(productList);

        // Assert
        assertThat(productResponseDtoList).isNotNull().hasSize(2);
        assertThat(productResponseDtoList.get(0).getId()).isEqualTo(product1.getId());
        assertThat(productResponseDtoList.get(0).getName()).isEqualTo(product1.getName());
        assertThat(productResponseDtoList.get(0).getDescription()).isEqualTo(product1.getDescription());
        assertThat(productResponseDtoList.get(0).getPrice()).isEqualTo(product1.getPrice());

        assertThat(productResponseDtoList.get(1).getId()).isEqualTo(product2.getId());
        assertThat(productResponseDtoList.get(1).getName()).isEqualTo(product2.getName());
        assertThat(productResponseDtoList.get(1).getDescription()).isEqualTo(product2.getDescription());
        assertThat(productResponseDtoList.get(1).getPrice()).isEqualTo(product2.getPrice());
    }
}