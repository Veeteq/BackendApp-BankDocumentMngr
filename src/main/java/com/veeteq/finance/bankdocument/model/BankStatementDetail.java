package com.veeteq.finance.bankdocument.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.veeteq.finance.bankdocument.util.DateUtil;

@Entity
@Table(name = "bank_statement_details")
public class BankStatementDetail extends BaseEntity<BankStatement> {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "deta_id")
    private Long id;
    
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "stmt_id", referencedColumnName = "stmt_id", nullable = false)
    private BankStatement bankStatement;
    
    @Column(name = "sequ_nm") 
    private Integer sequenceNumber;
    
    @Column(name = "oper_dt")
    private LocalDate operationDate; 
    
    @Column(name = "post_dt")
    private LocalDate postingDate;

    @Column(name = "oper_type_tx")
    @Enumerated(value = EnumType.STRING)
    private OperationType operationType;
    
    @Column(name = "oper_titl_tx")
    private String title;
    
    @Column(name = "cprt_name_tx")
    private String counterpartyName;
    
    @Column(name = "cprt_addr_tx")
    private String counterpartyAddress;
    
    @Column(name = "cprt_id")
    private Long counterpartyId;
    
    @Column(name = "cprt_iban_tx")
    private String counterpartyIban;
    
    @Column(name = "oper_am")
    private BigDecimal amount;
    
    @Column(name = "bala_am")
    private BigDecimal balance;
    
    public Long getId() {
        return this.id;
    }
    
    public BankStatementDetail setId(Long id) {
        this.id = id;
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

    public String getCounterpartyName() {
        return counterpartyName;
    }

    public BankStatementDetail setCounterpartyName(String counterpartyName) {
        this.counterpartyName = counterpartyName;
        return this;
    }

    public String getCounterpartyAddress() {
        return counterpartyAddress;
    }

    public BankStatementDetail setCounterpartyAddress(String counterpartyAddress) {
        this.counterpartyAddress = counterpartyAddress;
        return this;
    }

    public Long getCounterpartyId() {
        return counterpartyId;
    }

    public BankStatementDetail setCounterpartyId(Long counterpartyId) {
        this.counterpartyId = counterpartyId;
        return this;
    }

    public String getCounterpartyIban() {
        return counterpartyIban;
    }

    public BankStatementDetail setCounterpartyIban(String counterpartyIban) {
        this.counterpartyIban = counterpartyIban;
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
    
}
