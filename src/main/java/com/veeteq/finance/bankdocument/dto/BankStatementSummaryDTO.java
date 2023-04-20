package com.veeteq.finance.bankdocument.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BankStatementSummaryDTO {
    private Long id;
    private AccountDTO account;
    private LocalDate reportDate;
    private String fileName;
    private Integer itemsCount;
    private BigDecimal totalAmount;

    public Long getId() {
        return id;
    }

    public BankStatementSummaryDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public BankStatementSummaryDTO setAccount(AccountDTO account) {
        this.account = account;
        return this;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public BankStatementSummaryDTO setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public BankStatementSummaryDTO setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public Integer getItemsCount() {
        return itemsCount;
    }

    public BankStatementSummaryDTO setItemsCount(Integer itemsCount) {
        this.itemsCount = itemsCount;
        return this;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BankStatementSummaryDTO setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

}
