package com.neopos.adapters.controller;

import com.neopos.adapters.dto.request.ProductPostRequestDto;
import com.neopos.adapters.dto.response.Meta;
import com.neopos.adapters.dto.response.ProductGetDto;
import com.neopos.adapters.dto.response.Response;
import com.neopos.adapters.mapper.ProductMapper;
import com.neopos.adapters.utils.LinkBuilder;
import com.neopos.adapters.utils.Pagination;
import com.neopos.adapters.utils.ProductsUtils;
import com.neopos.adapters.utils.metabuilders.ProductsMetaBuilder;
import com.neopos.application.core.domain.Product;
import com.neopos.application.ports.input.FindProductByIdInputPort;
import com.neopos.application.ports.input.FindProductsInputPort;
import com.neopos.application.ports.input.InsertProductInputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final InsertProductInputPort insertProductInputPort;
    private final FindProductsInputPort findProductsInputPort;
    private final FindProductByIdInputPort findProductByIdInputPort;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;
    private final LinkBuilder linkBuilder;
    private final ProductsMetaBuilder productsMetaBuilder;
    private final ProductsUtils productsUtils;
    public static final String REQUEST_BASE_PATH = "api/v1/products";

    @PostMapping
    public ResponseEntity<Void> insertProduct(@Validated @RequestBody ProductPostRequestDto dto) {
        insertProductInputPort.insert(productMapper.toDomain(dto));
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Response<List<ProductGetDto>>> listProducts(
            @RequestParam(required = false, name = "page-number") Integer pageNumber,
            @RequestParam(required = false, name = "page-size") Integer pageSize
            ) {
        int queryPageNumber = Pagination.validatePageNumber(pageNumber);
        int queryPageSize = Pagination.validatePageSize(pageSize);
        Meta meta;
        List<ProductGetDto> data;
        Map<String, String> links;
        Response<List<ProductGetDto>> response;

        List<Product> products = findProductsInputPort.findAll(queryPageNumber, queryPageSize);

        if(linkBuilder.productsListIsEmpty(products)) {
            data = new ArrayList<>();
            links = new HashMap<>();
            meta = new Meta(0, 0, 0);
        } else {
            data = productMapper.toGetDtoList(products);
            data.forEach(productsUtils.buildSelfLinks);
            meta = productsMetaBuilder.build(queryPageSize, queryPageNumber);

            links = linkBuilder.buildPageLinks(REQUEST_BASE_PATH, meta.getTotalPages(), queryPageNumber, queryPageSize);
        }

        response = new Response<>(data, meta, links);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductGetDto> findProductById(@PathVariable("productId") String id) {
        ProductGetDto response = productMapper.toGetDto(findProductByIdInputPort.findById(id));
        response.setLinks(linkBuilder.buildSelfLink(REQUEST_BASE_PATH, id));
        return ResponseEntity.ok(response);
    }
}
