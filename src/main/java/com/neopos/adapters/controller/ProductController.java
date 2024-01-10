package com.neopos.adapters.controller;

import com.neopos.adapters.dto.request.ProductPostRequestDto;
import com.neopos.adapters.dto.response.ProductGetDto;
import com.neopos.adapters.dto.response.Response;
import com.neopos.adapters.mapper.ProductMapper;
import com.neopos.adapters.utils.Pagination;
import com.neopos.application.ports.input.FindProductsInputPort;
import com.neopos.application.ports.input.InsertProductInputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final InsertProductInputPort insertProductInputPort;
    private final FindProductsInputPort findProductsInputPort;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    private final Pagination<ProductGetDto> pagination = new Pagination<>();

    @PostMapping
    public ResponseEntity<Void> insertProduct(@Validated @RequestBody ProductPostRequestDto dto) {
        insertProductInputPort.insertProduct(productMapper.toDomain(dto));
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Response<List<ProductGetDto>>> listAll(
            @RequestParam(required = false, name = "page-size") Integer pageSize,
            @RequestParam(required = false, name = "page-number") Integer pageNumber
            ) {
        List<ProductGetDto> productGetDtoList = productMapper.toGetDtoList(findProductsInputPort.listProducts());

        PagedListHolder<ProductGetDto> page = pagination.build(pageNumber, pageSize, productGetDtoList);

        Response<List<ProductGetDto>> response = new Response<>();
        response.setData(page.getPageList());
        response.setLinks(pagination.buildPageLinks(page));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductGetDto> findProduct() {
        return null;
    }
}
