package com.neopos.adapter.service;

import com.neopos.adapter.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CountProductsAdapterTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CountProductsAdapter countProductsAdapter;

    @Test
    @DisplayName("Repository's count method should return a long")
    void count_ShouldReturnProductCountFromRepository() {
        given(productRepository.count()).willReturn(5L);

        int result = countProductsAdapter.count();

        assertThat(result).isEqualTo(5);
        verify(productRepository).count();
    }
}