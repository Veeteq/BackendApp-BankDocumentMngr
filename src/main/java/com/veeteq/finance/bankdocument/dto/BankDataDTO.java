package com.veeteq.finance.bankdocument.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
public class BankDataDTO implements Serializable {
    private static final long serialVersionUID = -7096385209920998903L;

    @JsonProperty(value = "id")
    private Long id;
    
    @JsonProperty(value = "title")
    private String title;

    @JsonProperty(value = "counterpartyIban")
    private String accountNumber;

    @JsonProperty(value = "counterpartyName")
    private String counterparty;

    @JsonProperty(value = "counterpartyAddress")
    private String counterpartyAddress;

    public Long getId() {
        return id;
    }

    public BankDataDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BankDataDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BankDataDTO setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public String getCounterparty() {
        return counterparty;
    }

    public BankDataDTO setCounterparty(String counterparty) {
        this.counterparty = counterparty;
        return this;
    }

    public String getCounterpartyAddress() {
        return counterpartyAddress;
    }

    public BankDataDTO setCounterpartyAddress(String counterpartyAddress) {
        this.counterpartyAddress = counterpartyAddress;
        return this;
    }

}
