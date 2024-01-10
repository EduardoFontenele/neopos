package com.neopos.application.ports.output;

import com.neopos.application.core.domain.Product;

import java.util.List;

public interface FindProductsOutputPort {
    List<Product> listProducts();
}
