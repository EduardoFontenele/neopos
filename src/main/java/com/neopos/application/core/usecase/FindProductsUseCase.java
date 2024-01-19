package com.neopos.application.core.usecase;

import com.neopos.application.core.domain.Product;
import com.neopos.application.ports.input.FindProductsInputPort;
import com.neopos.application.ports.output.CountProductsOutputPort;
import com.neopos.application.ports.output.FindProductsOutputPort;

import java.util.List;

public class FindProductsUseCase implements FindProductsInputPort {
    private final FindProductsOutputPort findProductsOutputPort;
    private final CountProductsOutputPort countProductsOutputPort;


    public FindProductsUseCase(FindProductsOutputPort findProductsOutputPort, CountProductsOutputPort countProductsOutputPort) {
        this.countProductsOutputPort = countProductsOutputPort;
        this.findProductsOutputPort = findProductsOutputPort;
    }

    @Override
    public List<Product> findAll(int pageNumber, int pageSize) {
        int queryPageNumber;
        double totalRecords = countProductsOutputPort.count();
        int totalPages = (int) Math.ceil(totalRecords / pageSize);

        // Usually, a pagination will start at 0, so if you want a page N, it's actually N - 1 that will be queried
        if(pageNumber > totalPages) {
            queryPageNumber = totalPages - 1;
        } else {
            queryPageNumber = pageNumber - 1;
        }

        return findProductsOutputPort.findAll(queryPageNumber, pageSize);
    }
}
