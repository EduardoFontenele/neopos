package com.neopos.application.ports.output;

import com.neopos.application.core.domain.Product;

import java.util.Map;

public interface UpdateProductByIdOutputPort {
    Product execute(Product product, Map<String, String> capturedErrors, String id);
}
