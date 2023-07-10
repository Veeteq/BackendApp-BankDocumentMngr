package com.veeteq.finance.bankdocument.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BankStatementInfoDTO {
  private Long id;
  private Integer sequenceNumber;
  private String operationType;

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate operationDate;

  private String title;
  private Long accountId;
  private Long counterpartyId;
  private BigDecimal amount;

  public Long getId() {
    return id;
  }

  public BankStatementInfoDTO setId(Long id) {
    this.id = id;
    return this;
  }

  public Integer getSequenceNumber() {
    return sequenceNumber;
  }

  public BankStatementInfoDTO setSequenceNumber(Integer sequenceNumber) {
    this.sequenceNumber = sequenceNumber;
    return this;
  }

  public String getOperationType() {
    return operationType;
  }

  public BankStatementInfoDTO setOperationType(String operationType) {
    this.operationType = operationType;
    return this;
  }

  public LocalDate getOperationDate() {
    return operationDate;
  }

  public BankStatementInfoDTO setOperationDate(LocalDate operationDate) {
    this.operationDate = operationDate;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public BankStatementInfoDTO setTitle(String title) {
    this.title = title;
    return this;
  }

  public Long getAccountId() {
    return accountId;
  }

  public BankStatementInfoDTO setAccountId(Long accountId) {
    this.accountId = accountId;
    return this;
  }

  public Long getCounterpartyId() {
    return counterpartyId;
  }

  public BankStatementInfoDTO setCounterpartyId(Long counterpartyId) {
    this.counterpartyId = counterpartyId;
    return this;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BankStatementInfoDTO setAmount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }
}
