package com.neopos.application.ports.input;

import com.neopos.application.core.domain.Product;

import java.util.List;

public interface FindProductsInputPort {
    List<Product> findAll(int pageNumber, int pageSize);
}
