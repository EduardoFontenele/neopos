package com.neopos.application.core.usecase;

import com.neopos.application.core.domain.Product;
import com.neopos.application.core.domain.ValidationMessages;
import com.neopos.application.ports.input.FindProductByIdInputPort;
import com.neopos.application.ports.output.FindProductByIdOutputPort;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindProductByIdUseCase implements FindProductByIdInputPort {
    private final FindProductByIdOutputPort findProductByIdOutputPort;

    public FindProductByIdUseCase(FindProductByIdOutputPort findProductByIdOutputPort) {
        this.findProductByIdOutputPort = findProductByIdOutputPort;
    }

    @Override
    public Product execute(String id, Map<String, String> capturedErrors) {
        if(id == null) {
            capturedErrors.put("id", ValidationMessages.ID_CANNOT_BE_NULL);
        } else {
            String uuidRegex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
            Pattern pattern = Pattern.compile(uuidRegex);
            Matcher matcher = pattern.matcher(id);

            if(!matcher.matches()) {
                capturedErrors.put("id", ValidationMessages.ID_HAS_WRONG_PATTERN);
            }
        }

        return findProductByIdOutputPort.findById(id, capturedErrors);
    }
}
