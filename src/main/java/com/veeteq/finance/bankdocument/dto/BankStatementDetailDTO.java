package com.veeteq.finance.bankdocument.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class BankStatementDetailDTO {

    private Long id;
    private Integer sequenceNumber;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate operationDate;

    private String operationType;
    
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate postingDate;

    private String title;
    private Long counterpartyId;
    private String counterpartyName;
    private String counterpartyIban;
    private String counterpartyAddress;
    private BigDecimal amount;
    private BigDecimal balance;

    public Long getId() {
        return id;
    }

    public BankStatementDetailDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public BankStatementDetailDTO setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        return this;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public BankStatementDetailDTO setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
        return this;
    }

    public String getOperationType() {
        return operationType;
    }

    public BankStatementDetailDTO setOperationType(String operationType) {
        this.operationType = operationType;
        return this;
    }

    public LocalDate getPostingDate() {
        return postingDate;
    }

    public BankStatementDetailDTO setPostingDate(LocalDate postingDate) {
        this.postingDate = postingDate;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BankStatementDetailDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public Long getCounterpartyId() {
        return counterpartyId;
    }

    public BankStatementDetailDTO setCounterpartyId(Long counterpartyId) {
        this.counterpartyId = counterpartyId;
        return this;
    }

    public String getCounterpartyName() {
        return counterpartyName;
    }

    public BankStatementDetailDTO setCounterpartyName(String counterpartyName) {
        this.counterpartyName = counterpartyName;
        return this;
    }

    public String getCounterpartyIban() {
        return counterpartyIban;
    }

    public BankStatementDetailDTO setCounterpartyIban(String counterpartyIban) {
        this.counterpartyIban = counterpartyIban;
        return this;
    }

    public String getCounterpartyAddress() {
        return counterpartyAddress;
    }

    public BankStatementDetailDTO setCounterpartyAddress(String counterpartyAddress) {
        this.counterpartyAddress = counterpartyAddress;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BankStatementDetailDTO setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BankStatementDetailDTO setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

}
