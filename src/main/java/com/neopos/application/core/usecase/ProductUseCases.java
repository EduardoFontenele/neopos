package com.neopos.application.core.usecase;

import com.neopos.application.core.domain.Product;
import com.neopos.application.ports.input.FindProductsInputPort;
import com.neopos.application.ports.input.InsertProductInputPort;
import com.neopos.application.ports.output.FindProductsOutputPort;
import com.neopos.application.ports.output.InsertProductOutputPort;

import java.util.List;

public class ProductUseCases implements InsertProductInputPort, FindProductsInputPort {

    private final InsertProductOutputPort insertProductOutputPort;
    private final FindProductsOutputPort findProductsOutputPort;

    public ProductUseCases(InsertProductOutputPort outputPort, FindProductsOutputPort findProductsOutputPort) {
        this.insertProductOutputPort = outputPort;
        this.findProductsOutputPort = findProductsOutputPort;
    }

    @Override
    public void insertProduct(Product product) {
        insertProductOutputPort.insertProduct(product);
    }

    @Override
    public List<Product> listProducts() {
        return findProductsOutputPort.listProducts();
    }
}
