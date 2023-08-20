package com.veeteq.finance.bankdocument.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BankStatementDetailId implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "stmt_id", nullable = false)
  private Long bankStatementId;

  @Column(name = "sequ_nm", nullable = false)
  private Integer sequenceNumber;

  public BankStatementDetailId() {
  }

  public BankStatementDetailId(Long bankStatementId, Integer sequenceNumber) {
    this.bankStatementId = bankStatementId;
    this.sequenceNumber = sequenceNumber;
  }

  public Long getBankStatementId() {
    return bankStatementId;
  }

  public BankStatementDetailId setBankStatementId(Long bankStatementId) {
    this.bankStatementId = bankStatementId;
    return this;
  }

  public Integer getSequenceNumber() {
    return sequenceNumber;
  }

  public BankStatementDetailId setSequenceNumber(Integer sequenceNumber) {
    this.sequenceNumber = sequenceNumber;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BankStatementDetailId)) return false;
    BankStatementDetailId that = (BankStatementDetailId) o;
    return Objects.equals(getBankStatementId(), that.getBankStatementId()) &&
            Objects.equals(getSequenceNumber(), that.getSequenceNumber());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getBankStatementId(), getSequenceNumber());
  }
}
