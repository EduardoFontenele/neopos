package com.neopos.application.core.usecase;

import com.neopos.application.core.domain.ValidationMessages;
import com.neopos.application.ports.output.FindProductByIdOutputPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FindProductByIdUseCaseUnitTest {
    @Mock
    private FindProductByIdOutputPort outputPort;
    @InjectMocks
    private FindProductByIdUseCase useCase;

    @Test
    @DisplayName("Given invalid ID value, when execute, then fill captured errors map")
    void givenInvalidId_whenExecute_thenFillCapturedErrorsMap() {
        final Map<String, String> capturedErrors = new HashMap<>();
        final String id = "1a5e5e58-0df8-44f8-b4ab-7c4278c3ec9";

        useCase.execute(id, capturedErrors);

        assertTrue(capturedErrors.containsKey("id"));
        assertEquals(ValidationMessages.ID_HAS_WRONG_PATTERN, capturedErrors.get("id"));
    }

    @Test
    @DisplayName("Given null ID value, when execute, then fill captured errors map")
    void givenNullId_whenExecute_thenFillCapturedErrorsMap() {
        final Map<String, String> capturedErrors = new HashMap<>();

        useCase.execute(null, capturedErrors);

        assertTrue(capturedErrors.containsKey("id"));
        assertEquals(ValidationMessages.ID_CANNOT_BE_NULL, capturedErrors.get("id"));
    }

    @Test
    @DisplayName("Given valid ID, when execute, captured errors should stay empty")
    void givenValidId_whenExecute_capturedErrorsShouldStayEmpty() {
        final Map<String, String> capturedErrors = new HashMap<>();

        useCase.execute(UUID.randomUUID().toString(), capturedErrors);

        assertTrue(capturedErrors.isEmpty());
    }
}