package com.veeteq.finance.bankdocument.model;

import java.util.Arrays;

import org.springframework.http.MediaType;

public enum FileType {
    APPLICATION_PDF("application/pdf", MediaType.APPLICATION_PDF),
    TEXT_HTML("text/html",             MediaType.TEXT_HTML);
    
    private final String code;
    private final MediaType mediaType;
    
    private FileType(String code, MediaType mediaType) {
        this.code = code;
        this.mediaType = mediaType;
    }

    public String getCode() {
        return code;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public static FileType findByCode(String code) {
        return Arrays.stream(values())
                .filter(el -> el.code.equals(code))
                .findFirst()
                .get();
    }
    
}
