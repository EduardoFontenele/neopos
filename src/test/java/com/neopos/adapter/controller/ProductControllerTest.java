package com.neopos.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neopos.adapter.dto.request.ProductPostRequestDto;
import com.neopos.adapter.dto.response.Meta;
import com.neopos.adapter.dto.response.ProductGetDto;
import com.neopos.adapter.dto.response.Response;
import com.neopos.adapter.utils.ProductFactories;
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
import java.util.HashMap;
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
    private ProductFactories productFactories;
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
        Response<List<ProductGetDto>> response = new Response<>(data, meta, new HashMap<>());

        given(findProductsInputPort.findAll(queryPageNumber, queryPageSize)).willReturn(productList);
        given(productFactories.buildPagedResponse(productList, queryPageNumber, queryPageSize)).willReturn(response);

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
        Response<List<ProductGetDto>> response = new Response<>(new ArrayList<>(), new Meta(), new HashMap<>());

        given(findProductsInputPort.findAll(queryPageNumber, queryPageSize)).willReturn(new ArrayList<>());
        given(productFactories.buildPagedResponse(new ArrayList<>(), queryPageNumber, queryPageSize)).willReturn(response);

        mockMvc.perform(get("/api/v1/products?page-number=1&page-size=3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.meta").exists())
                .andExpect(jsonPath("$.links").exists())
                .andExpect(jsonPath("$.data", hasSize(0)));
    }

    @Test
    @DisplayName("Given valid ID, when findById called, should return 200 and a ProductGetDto")
    void givenValidId_whenFindById_thenReturnProductGetDto() throws Exception {
        ProductGetDto productGetDto = ProductFixture.gimmeSingleProductGetDto();
        Product product = ProductFixture.gimmeSingleProduct();

        given(findProductByIdInputPort.findById(productGetDto.getId())).willReturn(product);
        given(productFactories.buildSingleResponse(product, product.getId())).willReturn(productGetDto);

        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(productGetDto.getId())));
    }
}