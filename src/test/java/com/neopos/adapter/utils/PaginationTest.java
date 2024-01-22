package com.neopos.adapter.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PaginationTest {
    @Test
    @DisplayName("Validate Page Number - Positive Case")
    void validatePageNumber_positiveCase() {
        // Arrange
        Integer pageNumber = 3;

        // Act
        Integer result = Pagination.validatePageNumber(pageNumber);

        // Assert
        assertThat(result).isEqualTo(pageNumber);
    }

    @Test
    @DisplayName("Validate Page Number - Null Case")
    void validatePageNumber_nullCase() {
        // Arrange
        Integer pageNumber = null;

        // Act
        Integer result = Pagination.validatePageNumber(pageNumber);

        // Assert
        assertThat(result).isEqualTo(Pagination.DEFAULT_PAGE_NUMBER);
    }

    @Test
    @DisplayName("Validate Page Size - Positive Case")
    void validatePageSize_positiveCase() {
        // Arrange
        Integer pageSize = 20;

        // Act
        Integer result = Pagination.validatePageSize(pageSize);

        // Assert
        assertThat(result).isEqualTo(pageSize);
    }

    @Test
    @DisplayName("Validate Page Size - Exceed Maximum Case")
    void validatePageSize_exceedMaximumCase() {
        // Arrange
        Integer pageSize = 60;

        // Act
        Integer result = Pagination.validatePageSize(pageSize);

        // Assert
        assertThat(result).isEqualTo(50);
    }

    @Test
    @DisplayName("Validate Page Size - Null Case")
    void validatePageSize_nullCase() {
        // Arrange
        Integer pageSize = null;

        // Act
        Integer result = Pagination.validatePageSize(pageSize);

        // Assert
        assertThat(result).isEqualTo(Pagination.DEFAULT_PAGE_SIZE);
    }
}