package com.neopos.application.ports.input;

import com.neopos.application.core.domain.Product;

public interface InsertProductInputPort {
    void insert(Product product);
}