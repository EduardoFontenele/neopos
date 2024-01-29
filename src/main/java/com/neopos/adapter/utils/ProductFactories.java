package com.neopos.adapter.utils;

import com.neopos.adapter.controller.ProductController;
import com.neopos.adapter.dto.request.ProductRequestDto;
import com.neopos.adapter.dto.response.Meta;
import com.neopos.adapter.dto.response.ProductResponseDto;
import com.neopos.adapter.dto.response.Response;
import com.neopos.adapter.entity.ProductEntity;
import com.neopos.adapter.mapper.ProductMapper;
import com.neopos.application.core.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductFactories {
    private final ProductMapper productMapper = ProductMapper.INSTANCE;
    private final LinkBuilder linkBuilder;
    private final ProductMetaBuilder productMetaBuilder;

    public Response<List<ProductResponseDto>> buildPagedResponse(List<Product> products, int queryPageNumber, int queryPageSize) {
        Meta meta;
        List<ProductResponseDto> data;
        Map<String, String> links;
        Response<List<ProductResponseDto>> response;

        if(products.isEmpty()) {
            data = new ArrayList<>();
            links = new HashMap<>();
            meta = new Meta(0, 0, 0);
        } else {
            data = productMapper.toGetDtoList(products);
            data.forEach(productGetDto -> productGetDto.setLinks(linkBuilder.buildSelfLink(ProductController.REQUEST_BASE_PATH, productGetDto.getId())));
            meta = productMetaBuilder.build(queryPageSize, queryPageNumber);

            links = linkBuilder.buildPageLinks(ProductController.REQUEST_BASE_PATH, meta.getTotalPages(), queryPageNumber, queryPageSize);
        }
        response = new Response<>(data, meta, links);
        return response;
    }

    public ProductResponseDto buildSingleResponse(Product product, String id) {
        ProductResponseDto response = productMapper.toGetDto(product);
        response.setLinks(linkBuilder.buildSelfLink(ProductController.REQUEST_BASE_PATH, id));
        return response;
    }

    public Product buildDomainObject(ProductRequestDto dto) {
        return productMapper.toDomain(dto);
    }

    public Product buildDomainObject(ProductEntity entity) {
        return productMapper.toDomain(entity);
    }

    public ProductEntity buildEntityObject(Product product) {
        return productMapper.toEntity(product);
    }
}
