package com.neopos.application.core.usecase;

import com.neopos.application.core.domain.Product;
import com.neopos.application.core.domain.ValidationMessages;
import com.neopos.application.ports.output.UpdateProductByIdOutputPort;
import com.neopos.fixture.ProductFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateProductByIdUseCaseUnitTest {
    @Mock
    private UpdateProductByIdOutputPort outputPort;
    @InjectMocks
    private UpdateProductByIdUseCase useCase;
    private final String id = UUID.randomUUID().toString();


    @Test
    @DisplayName("Given validated Product, when execute, captured errors should stay empty")
    void givenValidatedProduct_whenExecute_capturedErrorsShouldStayEmpty() {
        Product product = ProductFixture.gimmeSingleProduct();
        final Map<String, String> capturedErrors = new HashMap<>();

        useCase.execute(product, capturedErrors, id);

        assertTrue(capturedErrors.isEmpty());
    }

    @Test
    @DisplayName("Given name with length less than 3, when execute, should put validation error on map")
    void givenNameWithLengthLessThan3_whenExecute_shouldPutValidationErrorOnMap() {
        Product product = ProductFixture.gimmeSingleProduct();
        product.setName("ab"); // Set a name with length less than 3
        final Map<String, String> capturedErrors = new HashMap<>();

        useCase.execute(product, capturedErrors, id);

        assertTrue(capturedErrors.containsKey("name"));
        assertEquals(ValidationMessages.SIZE_BETWEEN_3_AND_255_ERROR, capturedErrors.get("name"));
    }

    @Test
    @DisplayName("Given name with length greater than 255, when execute, should put validation error on map")
    void givenNameWithLengthGreaterThan255_whenExecute_shouldPutValidationErrorOnMap() {
        Product product = ProductFixture.gimmeSingleProduct();
        product.setName("a".repeat(256)); // Set a name with length greater than 255
        final Map<String, String> capturedErrors = new HashMap<>();

        useCase.execute(product, capturedErrors, id);

        assertTrue(capturedErrors.containsKey("name"));
        assertEquals(ValidationMessages.SIZE_BETWEEN_3_AND_255_ERROR, capturedErrors.get("name"));
    }


    @Test
    @DisplayName("Given description with length less than 10, when execute, should put validation error on map")
    void givenDescriptionWithLengthLessThan10_whenExecute_shouldPutValidationErrorOnMap() {
        Product product = ProductFixture.gimmeSingleProduct();
        product.setDescription("Short"); // Set a description with length less than 10
        final Map<String, String> capturedErrors = new HashMap<>();

        useCase.execute(product, capturedErrors, id);

        assertTrue(capturedErrors.containsKey("description"));
        assertEquals(ValidationMessages.SIZE_BETWEEN_10_AND_500_ERROR, capturedErrors.get("description"));
    }

    @Test
    @DisplayName("Given description with length greater than 500, when execute, should put validation error on map")
    void givenDescriptionWithLengthGreaterThan500_whenExecute_shouldPutValidationErrorOnMap() {
        Product product = ProductFixture.gimmeSingleProduct();
        product.setDescription("a".repeat(501)); // Set a description with length greater than 500
        final Map<String, String> capturedErrors = new HashMap<>();

        useCase.execute(product, capturedErrors, id);

        assertTrue(capturedErrors.containsKey("description"));
        assertEquals(ValidationMessages.SIZE_BETWEEN_10_AND_500_ERROR, capturedErrors.get("description"));
    }

    @Test
    @DisplayName("Given integer digits out of limits, when execute, should put price digit limit error on errors map")
    void givenIntegerDigitsOutOfLimits_whenExecute_shouldPutPriceDigitLimitErrorOnErrorsMap() {
        Product product = ProductFixture.gimmeSingleProduct();
        product.setPrice(new BigDecimal("99999999.99"));
        final Map<String, String> capturedErrors = new HashMap<>();

        useCase.execute(product, capturedErrors, id);

        assertTrue(capturedErrors.containsKey("price"));
        assertEquals(ValidationMessages.PRICE_RANGE_ERROR, capturedErrors.get("price"));
    }

    @Test
    @DisplayName("Given decimal digits out of limits, when execute, should put price digit limit error on errors map")
    void givenDecimalDigitsOutOfLimits_whenExecute_shouldPutPriceDigitLimitErrorOnErrorsMap() {
        Product product = ProductFixture.gimmeSingleProduct();
        product.setPrice(new BigDecimal("99.99999"));
        final Map<String, String> capturedErrors = new HashMap<>();

        useCase.execute(product, capturedErrors, id);

        assertTrue(capturedErrors.containsKey("price"));
        assertEquals(ValidationMessages.PRICE_DIGIT_LIMIT_ERROR, capturedErrors.get("price"));
    }
}