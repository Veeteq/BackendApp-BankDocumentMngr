package com.veeteq.finance.bankdocument.util;

import javax.persistence.AttributeConverter;

import com.veeteq.finance.bankdocument.model.FileType;

public class FileTypeConverter implements AttributeConverter<FileType, String> {

    @Override
    public String convertToDatabaseColumn(FileType fileType) {
        return fileType.getCode();
    }

    @Override
    public FileType convertToEntityAttribute(String code) {
        return FileType.findByCode(code);
    } 

}
