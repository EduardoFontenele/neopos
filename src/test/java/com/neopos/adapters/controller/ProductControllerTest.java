package com.neopos.adapters.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neopos.adapters.dto.request.ProductPostRequestDto;
import com.neopos.adapters.dto.response.ProductGetDto;
import com.neopos.adapters.mapper.ProductMapper;
import com.neopos.application.core.domain.Product;
import com.neopos.application.ports.input.FindProductsInputPort;
import com.neopos.application.ports.input.InsertProductInputPort;
import com.neopos.fixture.ProductPostRequestDtoFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @MockBean
    private InsertProductInputPort insertProductInputPort;
    @MockBean
    private FindProductsInputPort findProductsInputPort;
    @MockBean
    private ProductMapper productMapper;
    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Given a valid ProductPostRequestDto, should return 200")
    void insertProduct_shouldReturnOk() throws Exception {
        given(productMapper.toDomain(new ProductPostRequestDto())).willReturn(new Product());
        ProductPostRequestDto dto = ProductPostRequestDtoFixture.createDefault();
        String jsonString = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Given a valid GET request, should return a page of Products")
    void listProducts_shouldReturnOk() throws Exception {
        List<Product> productGetDtos = new ArrayList<>();
        given(findProductsInputPort.listProducts()).willReturn(productGetDtos);

        mockMvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}