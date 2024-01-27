package com.neopos.application.core.domain;

public class ValidationMessages {

    // Range Errors
    public static final String SIZE_BETWEEN_3_AND_255_ERROR = "Name must be between 3 and 255 characters";
    public static final String SIZE_BETWEEN_10_AND_500_ERROR = "Description must be between 10 and 500 characters";
    public static final String PRICE_RANGE_ERROR = "Price must be between 0.01 and 999999.99";
    public static final String PRICE_DIGIT_LIMIT_ERROR = "Price must have up to 7 digits in total, with up to 2 decimal places";

    // ID errors
    public static final String ID_CANNOT_BE_NULL = "ID must not be a null value";
    public static final String ID_HAS_WRONG_PATTERN = "Product ID doesn't attend to required pattern (UUID)";
}
