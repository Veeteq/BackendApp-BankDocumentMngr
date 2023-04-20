package com.veeteq.finance.bankdocument.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankStatementDTO {

    private Long id;
    private AccountDTO account;
    private LocalDate statementDate;
    private BigDecimal openingBalance;
    private BigDecimal closingBalance;
    
    @JsonProperty(value = "fileSize")
    private long size;
    
    @JsonProperty(value = "fileType")
    private String contentType;
    
    private String fileName;
    
    private List<BankStatementDetailDTO> details = new LinkedList<BankStatementDetailDTO>();

    public Long getId() {
        return id;
    }

    public BankStatementDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public BankStatementDTO setAccount(AccountDTO account) {
        this.account = account;
        return this;
    }

    public long getSize() {
        return size;
    }

    public BankStatementDTO setSize(long size) {
        this.size = size;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public BankStatementDTO setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public BankStatementDTO setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public LocalDate getStatementDate() {
        return statementDate;
    }

    public BankStatementDTO setStatementDate(LocalDate statementDate) {
        this.statementDate = statementDate;
        return this;
    }

    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    public BankStatementDTO setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
        return this;
    }

    public BigDecimal getClosingBalance() {
        return closingBalance;
    }

    public BankStatementDTO setClosingBalance(BigDecimal closingBalance) {
        this.closingBalance = closingBalance;
        return this;
    }

    public List<BankStatementDetailDTO> getDetails() {
        return details;
    }

    public BankStatementDTO setDetails(List<BankStatementDetailDTO> details) {
        this.details = details;
        return this;
    }

    public BankStatementDTO addToDetails(BankStatementDetailDTO detail) {
        this.details.add(detail);
        return this;
    }

}
