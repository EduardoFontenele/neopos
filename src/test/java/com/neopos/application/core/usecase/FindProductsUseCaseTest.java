package com.neopos.application.core.usecase;

import com.neopos.application.core.domain.Product;
import com.neopos.application.ports.output.CountProductsOutputPort;
import com.neopos.application.ports.output.FindProductsOutputPort;
import com.neopos.fixture.ProductFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FindProductsUseCaseTest {

    @Mock
    private FindProductsOutputPort findProductsOutputPort;

    @Mock
    private CountProductsOutputPort countProductsOutputPort;

    @InjectMocks
    private FindProductsUseCase findProductsUseCase;

    @Test
    @DisplayName("Given valid page number and page size, when total pages is bigger than page number, should return a list of Product")
    void givenValidPageNumber_andPageSize_whenTotalPagesIsBiggerThanPageNumber_shouldReturnListOfProduct() {
        List<Product> products = ProductFixture.gimmeProductList();
        int queryPageNumber = 2;
        int queryPageSize = 5;

        given(countProductsOutputPort.count()).willReturn(10);
        given(findProductsOutputPort.findAll(queryPageNumber - 1, queryPageSize)).willReturn(products);

        List<Product> response = findProductsUseCase.execute(queryPageNumber, queryPageSize);

        assertNotNull(response);
        assertEquals(response.size(), products.size());
        assertThat(response.get(0).getId()).isEqualTo(products.get(0).getId());
    }

    @Test
    @DisplayName("Given valid page and size, when page number request param is greater than total pages, queried page number should be total pages - 1")
    void givenValidNumber_andSize_whenPageNumberIsGreaterThanTotalPages_shouldReturnTotalPagesLessOne() {
        List<Product> products = ProductFixture.gimmeProductList();
        int queryPageNumber = 2;
        int queryPageSize = 5;

        given(countProductsOutputPort.count()).willReturn(5);
        // Total pages = elementsCount / pageSize. In this case, 5/5, which is equal to 1.
        // So if the pageNumber is greater than the total pages, queryPageNumber assumes the value of
        // total pages - 1. Which means, 0.
        given(findProductsOutputPort.findAll(0, queryPageSize)).willReturn(products);

        List<Product> response = findProductsUseCase.execute(queryPageNumber, queryPageSize);

        assertNotNull(response);
        assertEquals(response.size(), products.size());
        assertThat(response.get(0).getId()).isEqualTo(products.get(0).getId());
    }
}