package com.neopos.application.core.usecase;

import com.neopos.application.core.domain.Product;
import com.neopos.application.core.domain.ValidationMessages;
import com.neopos.application.ports.input.InsertProductInputPort;
import com.neopos.application.ports.output.InsertProductOutputPort;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public class InsertProductUseCase implements InsertProductInputPort, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final InsertProductOutputPort insertProductOutputPort;

    public InsertProductUseCase(InsertProductOutputPort insertProductOutputPort) {
        this.insertProductOutputPort = insertProductOutputPort;
    }

    @Override
    public void execute(Product product, Map<String, String> capturedErrors) {
        validateName(product.getName(), capturedErrors);
        validateDescription(product.getDescription(), capturedErrors);
        validatePrice(product.getPrice(), capturedErrors);

        insertProductOutputPort.insert(product, capturedErrors);
    }

    private void validateName(String name, Map<String, String> capturedErrors) {
        if(name == null || name.length() < 3 || name.length() > 255) {
            capturedErrors.put("name", ValidationMessages.SIZE_BETWEEN_3_AND_255_ERROR);
        }
    }

    private void validateDescription(String description, Map<String, String> capturedErrors) {
        if(description == null || description.length() < 10 || description.length() > 500) {
            capturedErrors.put("description", ValidationMessages.SIZE_BETWEEN_10_AND_500_ERROR);
        }
    }

    private void validatePrice(BigDecimal price, Map<String, String> capturedErrors) {
        if (price == null || price.compareTo(new BigDecimal("0.01")) < 0 || price.compareTo(new BigDecimal("999999.99")) > 0) {
            capturedErrors.put("price", ValidationMessages.PRICE_RANGE_ERROR);
        } else {
            String[] priceParts = price.toString().split("\\.");
            int decimalDigits = priceParts.length > 1 ? priceParts[1].length() : 0;

            if (decimalDigits > 2) {
                capturedErrors.put("price", ValidationMessages.PRICE_DIGIT_LIMIT_ERROR);
            }
        }
    }
}
