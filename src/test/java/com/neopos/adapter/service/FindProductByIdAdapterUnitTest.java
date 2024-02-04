package com.neopos.adapter.service;

import com.neopos.adapter.entity.ProductEntity;
import com.neopos.adapter.exception.BusinessLogicException;
import com.neopos.adapter.mapper.ProductMapper;
import com.neopos.adapter.repository.ProductRepository;
import com.neopos.application.core.domain.Product;
import com.neopos.fixture.ProductEntityFixture;
import com.neopos.fixture.ProductFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FindProductByIdAdapterUnitTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private FindProductByIdAdapter findProductByIdAdapter;

    @Test
    @DisplayName("Given valid product ID and no validation errors, should return the corresponding product")
    void givenValidProductIdAndNoValidationErrors_shouldReturnProduct() {
        // Arrange
        String validProductId = "validProductId";
        ProductEntity productEntity = ProductEntityFixture.gimmeSingleProductEntity();
        Product expectedProduct = ProductFixture.gimmeSingleProduct();

        given(productRepository.findById(validProductId)).willReturn(Optional.of(productEntity));
        given(productMapper.toDomain(productEntity)).willReturn(expectedProduct);

        // Act
        Product result = findProductByIdAdapter.findById(validProductId, new HashMap<>());

        // Assert
        assertNotNull(result);
        assertEquals(expectedProduct, result);
    }

    @Test
    @DisplayName("Given invalid product ID (not found), should throw RESOURCE_NOT_FOUND exception")
    void givenInvalidProductId_shouldThrowResourceNotFoundException() {
        // Arrange
        String invalidProductId = "invalidProductId";

        given(productRepository.findById(invalidProductId)).willReturn(Optional.empty());

        // Act & Assert
        assertThrows(BusinessLogicException.class, () ->
                findProductByIdAdapter.findById(invalidProductId, new HashMap<>())
        );
    }

    @Test
    @DisplayName("Given validation errors, should throw FIELD_VALIDATION_ERROR exception")
    void givenValidationErrors_shouldThrowFieldValidationError() {
        // Arrange
        String productId = "someProductId";
        Map<String, String> validationErrors = new HashMap<>();
        validationErrors.put("field1", "error1");
        validationErrors.put("field2", "error2");

        // Act & Assert
        assertThrows(BusinessLogicException.class, () ->
                findProductByIdAdapter.findById(productId, validationErrors)
        );
    }
}