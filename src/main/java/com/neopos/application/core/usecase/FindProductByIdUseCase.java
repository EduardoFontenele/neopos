package com.neopos.application.core.usecase;

import com.neopos.application.core.domain.Product;
import com.neopos.application.ports.input.FindProductByIdInputPort;
import com.neopos.application.ports.output.FindProductByIdOutputPort;

public class FindProductByIdUseCase implements FindProductByIdInputPort {
    private final FindProductByIdOutputPort findProductByIdOutputPort;

    public FindProductByIdUseCase(FindProductByIdOutputPort findProductByIdOutputPort) {
        this.findProductByIdOutputPort = findProductByIdOutputPort;
    }

    @Override
    public Product findById(String id) {
        return findProductByIdOutputPort.findById(id);
    }
}
