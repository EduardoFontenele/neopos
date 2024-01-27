package com.neopos.application.ports.output;

import java.util.Map;

public interface DeleteProductByIdOutputPort {
    void execute(String id,  Map<String, String> capturedErrors);
}
