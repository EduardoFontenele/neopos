package com.neopos.application.ports.output;

import com.neopos.application.core.domain.Product;

public interface InsertProductOutputPort {
    void insertProduct(Product product);
}
