package com.neopos.application.ports.input;

import com.neopos.application.core.domain.Product;

import java.util.Map;

public interface UpdateProductByIdInputPort {
    Product execute(Product product, Map<String, String> capturedErrors, String id);
}
