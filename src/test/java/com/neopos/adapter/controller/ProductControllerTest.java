package com.neopos.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neopos.adapter.dto.request.ProductPostRequestDto;
import com.neopos.adapter.dto.response.Meta;
import com.neopos.adapter.dto.response.ProductGetDto;
import com.neopos.adapter.mapper.ProductMapper;
import com.neopos.adapter.utils.LinkBuilder;
import com.neopos.adapter.utils.metabuilders.ProductsMetaBuilder;
import com.neopos.application.core.domain.Product;
import com.neopos.application.ports.input.FindProductByIdInputPort;
import com.neopos.application.ports.input.FindProductsInputPort;
import com.neopos.application.ports.input.InsertProductInputPort;
import com.neopos.fixture.MetaFixture;
import com.neopos.fixture.ProductFixture;
import com.neopos.fixture.ProductPostRequestDtoFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @MockBean
    private InsertProductInputPort insertProductInputPort;
    @MockBean
    private FindProductsInputPort findProductsInputPort;
    @MockBean
    private FindProductByIdInputPort findProductByIdInputPort;
    @MockBean
    private ProductMapper productMapper;
    @MockBean
    private LinkBuilder linkBuilder;
    @MockBean
    private ProductsMetaBuilder productsMetaBuilder;
    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Given a valid ProductPostRequestDto, when insertMethod is called, should return 200")
    void insertProduct_shouldReturnOk() throws Exception {
        String json = objectMapper.writeValueAsString(ProductPostRequestDtoFixture.gimmeSingleProductPostDto());

        mockMvc.perform(post("/api/v1/products")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Given an invalid input, when insertProduct method is called, should return 400")
    public void whenProvidedInvalidInput_thenShouldReturn404() throws Exception {
        ProductPostRequestDto dto = ProductPostRequestDtoFixture.gimmeSingleProductPostDto();
        dto.setName("");
        String json = objectMapper.writeValueAsString(dto);


        mockMvc.perform(post("/api/v1/products")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Given valid page number and size, when listAll method is called, should return 200 and a page of products")
    public void whenListAllMethodCalledWithValidPageAndSize_thenShouldReturn200AndPageOfProducts() throws Exception {
        int queryPageNumber = 1;
        int queryPageSize = 3;
        Meta meta = MetaFixture.gimmeMeta();
        List<ProductGetDto> data = ProductFixture.gimmeProductGetDtoList();
        List<Product> productList = ProductFixture.gimmeProductList();

        given(findProductsInputPort.findAll(queryPageNumber, queryPageSize)).willReturn(productList);
        given(productMapper.toGetDtoList(productList)).willReturn(data);
        given(productsMetaBuilder.build(queryPageSize, queryPageNumber)).willReturn(meta);

        mockMvc.perform(get("/api/v1/products?page-number=1&page-size=3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.meta").exists())
                .andExpect(jsonPath("$.links").exists())
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[0].id", is("1")));
    }

    @Test
    @DisplayName("Given valid page number and size, when listAll method is called, should return 200 and a empty page of products")
    void givenValidPageNumberAndSize_whenListAllMethodIsCalled_shouldReturn200AndPageOfProducts() throws Exception {
        int queryPageNumber = 1;
        int queryPageSize = 3;
        Meta meta = MetaFixture.gimmeMeta();
        List<ProductGetDto> data = new ArrayList<>();
        List<Product> productList = new ArrayList<>();

        given(findProductsInputPort.findAll(queryPageNumber, queryPageSize)).willReturn(productList);
        given(productMapper.toGetDtoList(productList)).willReturn(data);
        given(productsMetaBuilder.build(queryPageSize, queryPageNumber)).willReturn(meta);

        mockMvc.perform(get("/api/v1/products?page-number=1&page-size=3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.meta").exists())
                .andExpect(jsonPath("$.links").exists())
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

}