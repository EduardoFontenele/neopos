package com.neopos.application.core.usecase;

import com.neopos.application.core.domain.Product;
import com.neopos.application.ports.input.FindProductByIdInputPort;
import com.neopos.application.ports.output.FindProductByIdOutputPort;

import java.util.Map;

public class FindProductByIdUseCase implements FindProductByIdInputPort {
    private final FindProductByIdOutputPort findProductByIdOutputPort;

    public FindProductByIdUseCase(FindProductByIdOutputPort findProductByIdOutputPort) {
        this.findProductByIdOutputPort = findProductByIdOutputPort;
    }

    @Override
    public Product execute(String id, Map<String, String> capturedErrors) {
        return findProductByIdOutputPort.findById(id);
    }
}
