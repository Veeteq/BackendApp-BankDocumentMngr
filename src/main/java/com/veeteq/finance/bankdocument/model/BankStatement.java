package com.veeteq.finance.bankdocument.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.veeteq.finance.bankdocument.util.FileTypeConverter;

@Entity
@Table(name = "bank_statements")
public class BankStatement extends BaseEntity<BankStatement> {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "stmt_id")
    private Long id;
    
    @Column(name = "acco_id", nullable = true)
    private Long accountId;

    @Column(name = "file_name_tx")
    private String fileName;

    @Column(name = "file_type_tx")
    @Convert(converter = FileTypeConverter.class)
    private FileType fileType;
    
    @Lob
    @Column(name = "file_atta_bl")
    private Byte[] attachment;

    @Column(name = "stmt_rprt_dt")
    private LocalDate reportDate;
    
    @Column(name = "stmt_open_am")
    private BigDecimal openingAmount;
    
    @Column(name = "stmt_clos_am")
    private BigDecimal closingAmount;
    
    @OneToMany(mappedBy = "bankStatement", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private List<BankStatementDetail> details = new ArrayList<>();

    public Long getId() {
        return this.id;
    }
    
    public BankStatement setId(Long id) {
        this.id = id;
        return this;
    }
    
    public Long getAccountId() {
        return accountId;
    }

    public BankStatement setAccountId(Long accountId) {
        this.accountId = accountId;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public BankStatement setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public FileType getFileType() {
        return fileType;
    }

    public BankStatement setFileType(FileType fileType) {
        this.fileType = fileType;
        return this;
    }

    public Byte[] getAttachment() {
        return attachment;
    }

    public BankStatement setAttachment(Byte[] attachment) {
        this.attachment = attachment;
        return this;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public BankStatement setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
        return this;
    }

    public BigDecimal getOpeningAmount() {
        return openingAmount;
    }

    public BankStatement setOpeningAmount(BigDecimal openingAmount) {
        this.openingAmount = openingAmount;
        return this;
    }

    public BigDecimal getClosingAmount() {
        return closingAmount;
    }

    public BankStatement setClosingAmount(BigDecimal closingAmount) {
        this.closingAmount = closingAmount;
        return this;
    }

    public List<BankStatementDetail> getDetails() {
        return details;
    }

    public BankStatement setDetails(List<BankStatementDetail> details) {
        this.details = details;
        return this;
    }

    public BankStatement addToDetails(BankStatementDetail detail) {
        detail.setBankStatement(this);
        this.details.add(detail);
        return this;
    }
    
}
