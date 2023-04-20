package com.veeteq.finance.bankdocument.dto;

public class AccountDTO {
    private Long id;
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
