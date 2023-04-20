package com.veeteq.finance.bankdocument.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.veeteq.finance.bankdocument.util.FileTypeConverter;

@Entity
@Table(name = "bank_statements")
@AttributeOverride(name = "id", column = @Column(name = "stmt_id"))
@SequenceGenerator(name = "default_seq", sequenceName = "stmt_seq", allocationSize = 1)
public class BankStatement extends BaseEntity<BankStatement> {
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acco_id", referencedColumnName = "user_id")
    private Account account;

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
    public BankStatement setId(Long id) {
        super.setId(id);
        return this;
    }
    
    public Account getAccount() {
        return account;
    }

    public BankStatement setAccount(Account account) {
        this.account = account;
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
    
    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public BankStatement setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public BankStatement setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
        return this;
    }

}
