package com.neopos.adapter.service;

import com.neopos.adapter.entity.ProductEntity;
import com.neopos.adapter.mapper.ProductMapper;
import com.neopos.adapter.repository.ProductRepository;
import com.neopos.application.core.domain.Product;
import com.neopos.fixture.ProductEntityFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FindProductsAdapterTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private FindProductsAdapter findProductsAdapter;

    @Test
    @DisplayName("Given valid pageNumber and pageSize, and elements count greater than 0, should return list of products")
    void givenValidPageNumberPageSize_andElementsCountGreaterThanZero_shouldReturnListOfProducts() {
        int pageNumber = 0;
        int pageSize = 3;
        List<ProductEntity> productEntityList = ProductEntityFixture.gimmeProductEntityList();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<ProductEntity> productEntityPage = new PageImpl<>(productEntityList, pageRequest, productEntityList.size());

        /*
        given(productRepository.count()).willReturn((long) productEntityList.size());
        given(productRepository.findAll(pageRequest)).willReturn(productEntityPage);
        */
        when(productRepository.count()).thenReturn((long) productEntityList.size());
        when(productRepository.findAll(pageRequest)).thenReturn(productEntityPage);

        List<Product> response = findProductsAdapter.findAll(pageNumber, pageSize);

        assertNotNull(response);
        assertEquals(response.size(), productEntityList.size());
        assertEquals(response.get(0).getId(), productEntityList.get(0).getId());
    }

    @Test
    @DisplayName("Given valid pageNumber and pageSize, and elements count is 0, should return an empty array list")
    void givenValidPageNumberPageSize_andElementsCountIsZero_shouldReturnEmptyArrayList() {
        int pageNumber = 0;
        int pageSize = 3;
        List<ProductEntity> productEntityList = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<ProductEntity> productEntityPage = new PageImpl<>(productEntityList, pageRequest, 0);

        given(productRepository.count()).willReturn(0L);
        given(productRepository.findAll(pageRequest)).willReturn(productEntityPage);

        List<Product> response = findProductsAdapter.findAll(pageNumber, pageSize);

        assertNotNull(response);
        assertEquals(response.size(), 0);
        assertEquals(response.size(), productEntityList.size());
    }
}