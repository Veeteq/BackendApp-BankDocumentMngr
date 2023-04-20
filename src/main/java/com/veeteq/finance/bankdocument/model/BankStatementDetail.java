package com.veeteq.finance.bankdocument.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.veeteq.finance.bankdocument.util.DateUtil;

@Entity
@Table(name = "bank_statement_details")
@AttributeOverride(name = "id", column = @Column(name = "deta_id"))
@SequenceGenerator(name = "default_seq", sequenceName = "deta_seq", allocationSize = 1)
public class BankStatementDetail extends BaseEntity<BankStatement> {

    private static final long serialVersionUID = 1L;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "stmt_id", referencedColumnName = "stmt_id", nullable = false)
    private BankStatement bankStatement;
    
    @Column(name = "sequ_nm", columnDefinition = "tinyint") 
    private Integer sequenceNumber;
    
    @Column(name = "oper_dt")
    private LocalDate operationDate; 
    
    @Column(name = "post_dt")
    private LocalDate postingDate;

    @Column(name = "oper_type_tx")
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    
    @Column(name = "titl_tx")
    private String title;
    
    @Column(name = "cprt_tx")
    private String counterparty;
    
    @Column(name = "cprt_addr_tx")
    private String counterpartyAddress;
    
    @Column(name = "acco_numb_tx")
    private String accountNumber;
    
    @Column(name = "oper_am")
    private BigDecimal amount;
    
    @Column(name = "bala_am")
    private BigDecimal balance;
    
    @CreationTimestamp
    @Column(name = "crea_dt", nullable = false, updatable = false)
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    @Column(name = "updt_dt", nullable = false, updatable = true)
    private LocalDateTime updateDateTime;

    @Override
    public Long getId() {
        return super.getId();
    }
    
    @Override
    public BankStatementDetail setId(Long id) {
        super.setId(id);
        return this;
    }
    
    public BankStatement getBankStatement() {
        return bankStatement;
    }

    public BankStatementDetail setBankStatement(BankStatement bankStatement) {
        this.bankStatement = bankStatement;
        return this;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public BankStatementDetail setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        return this;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public BankStatementDetail setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
        return this;
    }

    public BankStatementDetail setOperationDate(String operationDateTxt) {
        this.operationDate = DateUtil.parse(operationDateTxt);
        return this;
    }
    
    public LocalDate getPostingDate() {
        return postingDate;
    }

    public BankStatementDetail setPostingDate(LocalDate postingDate) {
        this.postingDate = postingDate;
        return this;
    }
    
    public BankStatementDetail setPostingDate(String postingDateTxt) {
        this.postingDate = DateUtil.parse(postingDateTxt);
        return this;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public BankStatementDetail setOperationType(OperationType operationType) {
        this.operationType = operationType;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BankStatementDetail setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCounterparty() {
        return counterparty;
    }

    public String getCounterpartyAddress() {
        return counterpartyAddress;
    }

    public BankStatementDetail setCounterpartyAddress(String counterpartyAddress) {
        this.counterpartyAddress = counterpartyAddress;
        return this;
    }

    public BankStatementDetail setCounterparty(String counterparty) {
        this.counterparty = counterparty;
        return this;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BankStatementDetail setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BankStatementDetail setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BankStatementDetail setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }
    
}