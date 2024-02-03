package com.neopos.adapter.service;

import com.neopos.adapter.exception.BusinessLogicException;
import com.neopos.adapter.exception.ExceptionsTable;
import com.neopos.adapter.repository.ProductRepository;
import com.neopos.application.core.domain.ValidationMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteProductByIdAdapterUnitTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private DeleteProductByIdAdapter deleteProductByIdAdapter;

    private String productId;
    Map<String, String> capturedErrorsOnMethodCall;
    Map<String, String> capturedErrorsAfterException;

    @Test
    @DisplayName("Given an invalid ID, when execute, should throw a BusinessLogicException")
    @Rollback
    void testExecuteWithInvalidId() {
        productId = "invalidId";
        capturedErrorsOnMethodCall = new LinkedHashMap<>();
        capturedErrorsOnMethodCall.put("id", ValidationMessages.ID_HAS_WRONG_PATTERN);

        BusinessLogicException exception = assertThrows(BusinessLogicException.class, () -> {
            deleteProductByIdAdapter.execute(productId, capturedErrorsOnMethodCall);
        });

        assertEquals(ValidationMessages.ID_HAS_WRONG_PATTERN, exception.getCapturedErrors().get("id"));
        assertEquals(ExceptionsTable.FIELD_VALIDATION_ERROR.getMessage(), exception.getErrorMessage());
        verify(repository, times(0)).existsById(productId);
    }

    @Test
    @DisplayName("Given a valid ID for a non existent product, when execute, should throw a BusinessLogicException")
    void shouldThrowBusinessLogicExceptionForValidNonExistentProductId() {
        productId = UUID.randomUUID().toString();
        capturedErrorsOnMethodCall = Collections.emptyMap();
        capturedErrorsAfterException = new HashMap<>();
        capturedErrorsAfterException.put("id", String.format("Product with ID %s is not present", productId));

        given(repository.existsById(productId)).willReturn(false);

        BusinessLogicException exception = assertThrows(BusinessLogicException.class, () -> {
            deleteProductByIdAdapter.execute(productId, capturedErrorsOnMethodCall);
        });

        assertEquals(capturedErrorsAfterException.get("id"), exception.getCapturedErrors().get("id"));
        assertEquals(ExceptionsTable.RESOURCE_NOT_FOUND.getMessage(), exception.getErrorMessage());
        verify(repository, times(1)).existsById(productId);
    }

    @Test
    @DisplayName("Given a valid ID and an existent product, when execute, should call delete method on repository")
    void shouldCallDeleteMethodOnRepositoryForValidExistentProductId() {
        productId = UUID.randomUUID().toString();
        capturedErrorsOnMethodCall = Collections.emptyMap();

        given(repository.existsById(productId)).willReturn(true);

        deleteProductByIdAdapter.execute(productId, capturedErrorsOnMethodCall);

        verify(repository, times(1)).existsById(productId);
    }
}
