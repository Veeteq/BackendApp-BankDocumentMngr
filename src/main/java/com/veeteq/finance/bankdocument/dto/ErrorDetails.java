package com.veeteq.finance.bankdocument.dto;

import java.util.Date;

public class ErrorDetails {

    private final Date date;
    private final String message;
    private final String description;
    
    public ErrorDetails(Date date, String message, String description) {
        this.date = date;
        this.message = message;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

}
