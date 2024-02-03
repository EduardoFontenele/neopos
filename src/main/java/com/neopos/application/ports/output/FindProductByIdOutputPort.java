package com.neopos.application.ports.output;

import com.neopos.application.core.domain.Product;

import java.util.Map;

public interface FindProductByIdOutputPort {
    Product findById(String id, Map<String, String> capturedErrors);
}
