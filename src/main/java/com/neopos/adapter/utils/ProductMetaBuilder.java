package com.neopos.adapter.utils;

import com.neopos.adapter.dto.response.Meta;
import com.neopos.application.ports.output.CountProductsOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductMetaBuilder {

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
