package com.neopos.adapters.utils.metabuilders;

import com.neopos.adapters.dto.response.Meta;
import com.neopos.adapters.repository.ProductRepository;
import com.neopos.adapters.utils.Pagination;
import com.neopos.application.ports.output.CountProductsOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductsMetaBuilder {

    private final CountProductsOutputPort productRepository;

    public Meta build(Integer pageSize, Integer pageNumber) {
        int queryPageSize;

        if(pageSize == null) {
            queryPageSize = Pagination.DEFAULT_PAGE_SIZE;
        } else {
            queryPageSize = pageSize;
        }

        double totalItems = productRepository.count();
        double totalPages = Math.ceil(totalItems / queryPageSize);

        return Meta.builder()
                .totalItems(productRepository.count())
                .totalPages((int) totalPages)
                .currentPage(pageNumber)
                .build();
    }
}
