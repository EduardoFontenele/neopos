package com.neopos.adapters.utils;

import com.neopos.adapters.controller.ProductController;
import com.neopos.adapters.dto.response.ProductGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;


@Service
@RequiredArgsConstructor
public class ProductsUtils {
    private final LinkBuilder linkBuilder = new LinkBuilder();
    public Consumer<ProductGetDto> buildSelfLinks = productGetDto -> {
        productGetDto.setLinks(linkBuilder.buildSelfLink(ProductController.REQUEST_BASE_PATH, productGetDto.getId()));
    };
}
