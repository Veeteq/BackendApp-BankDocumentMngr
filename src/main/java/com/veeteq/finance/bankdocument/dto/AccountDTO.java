package com.veeteq.finance.bankdocument.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountDTO {
    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "displayName")
    private String name;

    public Long getId() {
        return id;
    }

    public AccountDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AccountDTO setName(String name) {
        this.name = name;
        return this;
    }

}
