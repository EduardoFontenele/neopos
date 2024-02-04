package com.neopos.adapter.service;

import com.neopos.adapter.entity.ProductEntity;
import com.neopos.adapter.exception.BusinessLogicException;
import com.neopos.adapter.repository.ProductRepository;
import com.neopos.adapter.utils.ProductFactories;
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
class UpdateProductByIdAdapterTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductFactories productFactories;

    @InjectMocks
    private UpdateProductByIdAdapter updateProductByIdAdapter;

    @Test
    @DisplayName("Given valid product and ID, and no validation errors, should return the updated product")
    void givenValidProductAndIdAndNoValidationErrors_shouldReturnUpdatedProduct() {
        // Arrange
        String productId = "validProductId";
        Product inputProduct = ProductFixture.gimmeSingleProduct();
        ProductEntity existingProductEntity = ProductEntityFixture.gimmeSingleProductEntity();
        ProductEntity updatedProductEntity = ProductEntityFixture.gimmeSingleProductEntity();
        Product updatedProduct = ProductFixture.gimmeSingleProduct();

        given(productRepository.existsById(productId)).willReturn(true);
        given(productRepository.findById(productId)).willReturn(Optional.of(existingProductEntity));
        given(productRepository.save(existingProductEntity)).willReturn(updatedProductEntity);
        given(productFactories.buildDomainObject(updatedProductEntity)).willReturn(updatedProduct);

        // Act
        Product result = updateProductByIdAdapter.execute(inputProduct, new HashMap<>(), productId);

        // Assert
        assertNotNull(result);
        assertEquals(updatedProduct, result);
    }

    @Test
    @DisplayName("Given non-existent product ID, should throw RESOURCE_NOT_FOUND exception")
    void givenNonExistentProductId_shouldThrowResourceNotFoundException() {
        // Arrange
        String nonExistentProductId = "nonExistentProductId";

        given(productRepository.existsById(nonExistentProductId)).willReturn(false);

        // Act & Assert
        assertThrows(BusinessLogicException.class, () ->
                updateProductByIdAdapter.execute(ProductFixture.gimmeSingleProduct(), new HashMap<>(), nonExistentProductId)
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
                updateProductByIdAdapter.execute(ProductFixture.gimmeSingleProduct(), validationErrors, productId)
        );
    }
}