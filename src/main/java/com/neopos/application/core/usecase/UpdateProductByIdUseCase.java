package com.neopos.application.core.usecase;

import com.neopos.application.core.domain.Product;
import com.neopos.application.core.domain.ValidationMessages;
import com.neopos.application.ports.input.UpdateProductByIdInputPort;
import com.neopos.application.ports.output.UpdateProductByIdOutputPort;

import java.math.BigDecimal;
import java.util.Map;

public class UpdateProductByIdUseCase implements UpdateProductByIdInputPort {

    private final UpdateProductByIdOutputPort updateProductByIdOutputPort;

    public UpdateProductByIdUseCase(UpdateProductByIdOutputPort updateProductByIdOutputPort) {
        this.updateProductByIdOutputPort = updateProductByIdOutputPort;
    }

    @Override
    public Product execute(Product product, Map<String, String> capturedErrors, String id) {
        if(product.getName() != null) {
            validateName(product.getName(), capturedErrors);
        }

        if(product.getDescription() != null) {
            validateDescription(product.getDescription(), capturedErrors);
        }

        if(product.getPrice() != null) {
            validatePrice(product.getPrice(), capturedErrors);
        }

        return updateProductByIdOutputPort.execute(product, capturedErrors, id);
    }

    private void validateName(String name, Map<String, String> capturedErrors) {
        if(name.length() < 3 || name.length() > 255) {
            capturedErrors.put("name", ValidationMessages.SIZE_BETWEEN_3_AND_255_ERROR);
        }
    }

    private void validateDescription(String description, Map<String, String> capturedErrors) {
        if(description.length() < 10 || description.length() > 500) {
            capturedErrors.put("description", ValidationMessages.SIZE_BETWEEN_10_AND_500_ERROR);
        }
    }

    private void validatePrice(BigDecimal price, Map<String, String> capturedErrors) {
        if(price != null) {
            if (price.compareTo(new BigDecimal("0.01")) < 0 || price.compareTo(new BigDecimal("999999.99")) > 0) {
                capturedErrors.put("price", ValidationMessages.PRICE_RANGE_ERROR);
            }

            String[] priceParts = price.toString().split("\\.");
            int integerDigits = priceParts[0].length();
            int decimalDigits = priceParts.length > 1 ? priceParts[1].length() : 0;

            if (integerDigits > 7 || decimalDigits > 2) {
                capturedErrors.put("price", ValidationMessages.PRICE_DIGIT_LIMIT_ERROR);
            }
        }
    }
}
