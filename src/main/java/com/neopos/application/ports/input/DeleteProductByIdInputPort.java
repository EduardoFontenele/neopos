package com.neopos.application.ports.input;

import java.util.Map;

public interface DeleteProductByIdInputPort {
    void execute(String id, Map<String, String> capturedErrors);
}
