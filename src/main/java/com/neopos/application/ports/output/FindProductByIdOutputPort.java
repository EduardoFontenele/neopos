package com.neopos.application.ports.output;

import com.neopos.application.core.domain.Product;

public interface FindProductByIdOutputPort {
    Product findById(String id);
}
