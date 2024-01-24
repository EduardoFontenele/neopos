package com.neopos.application.ports.output;

import com.neopos.application.core.domain.Product;

import java.util.Map;

public interface InsertProductOutputPort {
    void insert(Product product, Map<String, String> capturedErrors);
}
