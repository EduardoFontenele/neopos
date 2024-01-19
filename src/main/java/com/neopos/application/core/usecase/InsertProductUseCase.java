package com.neopos.application.core.usecase;

import com.neopos.application.core.domain.Product;
import com.neopos.application.ports.input.InsertProductInputPort;
import com.neopos.application.ports.output.InsertProductOutputPort;

public class InsertProductUseCase implements InsertProductInputPort {
    private final InsertProductOutputPort insertProductOutputPort;

    public InsertProductUseCase(InsertProductOutputPort insertProductOutputPort) {
        this.insertProductOutputPort = insertProductOutputPort;
    }

    @Override
    public void insert(Product product) {
        insertProductOutputPort.insert(product);
    }
}
