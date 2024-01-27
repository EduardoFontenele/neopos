package com.neopos.application.core.usecase;

import com.neopos.application.core.domain.ValidationMessages;
import com.neopos.application.ports.input.DeleteProductByIdInputPort;
import com.neopos.application.ports.output.DeleteProductByIdOutputPort;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteProductByIdUseCase implements DeleteProductByIdInputPort {

    private final DeleteProductByIdOutputPort deleteProductByIdOutputPort;

    public DeleteProductByIdUseCase(DeleteProductByIdOutputPort deleteProductByIdOutputPort) {
        this.deleteProductByIdOutputPort = deleteProductByIdOutputPort;
    }

    @Override
    public void execute(String id,  Map<String, String> capturedErrors) {
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

        deleteProductByIdOutputPort.execute(id, capturedErrors);
    }
}
