package com.neopos.adapter.controller;

import com.neopos.adapter.dto.request.ProductPostRequestDto;
import com.neopos.adapter.dto.response.ProductGetDto;
import com.neopos.adapter.dto.response.Response;
import com.neopos.adapter.utils.Pagination;
import com.neopos.adapter.utils.ProductFactories;
import com.neopos.application.core.domain.Product;
import com.neopos.application.ports.input.FindProductByIdInputPort;
import com.neopos.application.ports.input.FindProductsInputPort;
import com.neopos.application.ports.input.InsertProductInputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

    private final InsertProductInputPort insertProductInputPort;
    private final FindProductsInputPort findProductsInputPort;
    private final FindProductByIdInputPort findProductByIdInputPort;

    private final ProductFactories productFactories;
    public static final String REQUEST_BASE_PATH = "api/v1/products";

    @PostMapping
    public ResponseEntity<Void> insertProduct(@Validated @RequestBody ProductPostRequestDto dto) {
        log.info("Init DTO mapping to domain object");
        Product product = productFactories.buildDomainObject(dto);

        log.info("Init insert product operation");
        insertProductInputPort.insert(product);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Response<List<ProductGetDto>>> listProducts(
            @RequestParam(required = false, name = "page-number") Integer pageNumber,
            @RequestParam(required = false, name = "page-size") Integer pageSize
            ) {
        int queryPageNumber = Pagination.validatePageNumber(pageNumber);
        int queryPageSize = Pagination.validatePageSize(pageSize);

        final List<Product> products = findProductsInputPort.findAll(queryPageNumber, queryPageSize);
        final Response<List<ProductGetDto>> response = productFactories.buildPagedResponse(products, queryPageNumber, queryPageSize);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductGetDto> findProductById(@PathVariable("productId") String id) {
        final Product product = findProductByIdInputPort.findById(id);
        final ProductGetDto response = productFactories.buildSingleResponse(product, id);

        return ResponseEntity.ok(response);
    }
}
