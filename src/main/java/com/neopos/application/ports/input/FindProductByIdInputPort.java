package com.neopos.application.ports.input;

import com.neopos.application.core.domain.Product;

import java.util.Map;

public interface FindProductByIdInputPort {
    Product execute(String id, Map<String, String> capturedErrors);
}
