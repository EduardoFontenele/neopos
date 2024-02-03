package com.neopos.adapter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neopos.adapter.entity.ProductEntity;
import com.neopos.adapter.exception.BusinessLogicException;
import com.neopos.adapter.repository.ProductRepository;
import com.neopos.adapter.utils.ProductFactories;
import com.neopos.application.core.domain.Product;
import com.neopos.application.core.domain.ValidationMessages;
import com.neopos.fixture.ProductFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InsertProductAdapterUnitTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductFactories productFactories;
    @InjectMocks
    private InsertProductAdapter insertProductAdapter;

    private Map<String, String> capturedErrorsOnMethodCall;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    @DisplayName("Given a not empty capturedErrors, when insert method, should throw BusinessLogicException")
    void givenCapturedErrorsNotEmpty_whenInsert_shouldThrowBusinessLogicExc() throws JsonProcessingException {
        capturedErrorsOnMethodCall = new HashMap<>();
        capturedErrorsOnMethodCall.put("name", ValidationMessages.SIZE_BETWEEN_3_AND_255_ERROR);
        Product product = new Product();

        var result = assertThrows(BusinessLogicException.class, () -> insertProductAdapter.insert(product, capturedErrorsOnMethodCall));

        String jsonContent = objectMapper.writeValueAsString(result);
        final var resultNodes = objectMapper.readTree(jsonContent);

        assertAll("Fields validation",
                () -> assertTrue(resultNodes.has("errorMessage")),
                () -> assertTrue(resultNodes.has("capturedErrors")),
                () -> assertTrue(resultNodes.has("httpStatus")));
    }

    @Test
    @DisplayName("Given an empty capturedErrors map, when insert method, should call repository and conclude insertion")
    void givenEmptyCapturedErrors_whenInsert_shouldInsert() {
        capturedErrorsOnMethodCall = new HashMap<>();
        final Product product = ProductFixture.gimmeSingleProduct();
        final ProductEntity entity = ProductFixture.gimmeSingleProductEntity();

        given(productFactories.buildEntityObject(product)).willReturn(entity);

        insertProductAdapter.insert(product, capturedErrorsOnMethodCall);

        verify(productFactories, times(1)).buildEntityObject(product);
        verify(productRepository, times(1)).save(entity);
    }
}