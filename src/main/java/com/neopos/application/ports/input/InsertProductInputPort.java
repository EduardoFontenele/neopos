package com.neopos.application.ports.input;

import com.neopos.application.core.domain.Product;

import java.util.Map;

public interface InsertProductInputPort {
    void execute(Product product, Map<String, String> capturedErrors);
}
