package com.neopos.fixture;

import com.neopos.adapter.controller.ProductController;

import java.util.HashMap;
import java.util.Map;

public class LinksFixture {
    public static Map<String, String> gimmeSingleProductLink() {
        Map<String, String> productLink = new HashMap<>();
        productLink.put("self", String.format("http://localhost:8080/%s/%d", ProductController.REQUEST_BASE_PATH, 1));
        productLink.put("rel", String.format("http://localhost:8080/%s", ProductController.REQUEST_BASE_PATH));
        return productLink;
    }

    public static Map<String, String> gimmeSinglePageWithThreeProductsLink() {
        Map<String, String> pageLinks = new HashMap<>();
        pageLinks.put("self", "http://localhost:8080/api/v1/products?page-number=1&page-size=3");
        pageLinks.put("first", "http://localhost:8080/api/v1/products?page-number=1&page-size=3");
        pageLinks.put("last", "http://localhost:8080/api/v1/products?page-number=1&page-size=3");
        return pageLinks;
    }
}
