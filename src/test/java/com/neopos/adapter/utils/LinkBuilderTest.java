package com.neopos.adapter.utils;


import com.neopos.adapter.controller.ProductController;
import com.neopos.application.core.domain.Product;
import com.neopos.fixture.LinksFixture;
import com.neopos.fixture.ProductFixture;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class LinkBuilderTest {

    private static LinkBuilder linkBuilder;

    @BeforeAll
    static void setUp() {
        linkBuilder = new LinkBuilder("http://localhost:8080");
    }

    @Test
    @DisplayName("Given an URI path and an ID, should return a map with links")
    void buildSelfLink_shouldReturnMapWithLinks() {
        String self = LinksFixture.gimmeSingleProductLink().get("self");
        String rel = LinksFixture.gimmeSingleProductLink().get("rel");
        Product product = ProductFixture.gimmeSingleProduct();

        String path = ProductController.REQUEST_BASE_PATH;
        Map<String, String> response = linkBuilder.buildSelfLink(path, product.getId());

        assertNotNull(response);
        assertEquals(response.get("rel"), rel);
        assertEquals(response.get("self"), self);
    }

}