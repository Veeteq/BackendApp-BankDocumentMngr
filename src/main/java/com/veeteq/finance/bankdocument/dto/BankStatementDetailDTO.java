package com.veeteq.finance.bankdocument.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BankStatementDetailDTO {

    private Long id;
    private Integer sequenceNumber;
    private LocalDate operationDate;
    private String operationType;
    private LocalDate postingDate;
    private String title;
    private String accountNumber;
    private String counterparty;
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public BankStatementDetailDTO setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public String getCounterparty() {
        return counterparty;
    }

    public BankStatementDetailDTO setCounterparty(String counterparty) {
        this.counterparty = counterparty;
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
