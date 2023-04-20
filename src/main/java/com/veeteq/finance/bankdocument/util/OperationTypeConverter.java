package com.veeteq.finance.bankdocument.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.veeteq.finance.bankdocument.model.OperationType;

@Converter(autoApply = true)
public class OperationTypeConverter implements AttributeConverter<OperationType, String> {

    @Override
    public String convertToDatabaseColumn(OperationType operationType) {
        return operationType.getCode();
    }

    @Override
    public OperationType convertToEntityAttribute(String code) {
        return OperationType.findByCode(code);
    }
}
