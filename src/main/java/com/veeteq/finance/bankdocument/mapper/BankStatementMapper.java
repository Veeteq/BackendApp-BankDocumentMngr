package com.veeteq.finance.bankdocument.mapper;

import java.math.BigDecimal;

import com.veeteq.finance.bankdocument.dto.BankStatementDTO;
import com.veeteq.finance.bankdocument.dto.BankStatementDetailDTO;
import com.veeteq.finance.bankdocument.dto.BankStatementSummaryDTO;
import com.veeteq.finance.bankdocument.model.BankStatement;
import com.veeteq.finance.bankdocument.model.BankStatementDetail;
import com.veeteq.finance.bankdocument.model.FileType;
import com.veeteq.finance.bankdocument.model.OperationType;

public class BankStatementMapper {
private AccountMapper accountMapper = new AccountMapper();

    public BankStatementDTO toDto(BankStatement entity) {
        if (entity == null) {
            return null;
        }
        
        BankStatementDTO dto = new BankStatementDTO()
                .setId(entity.getId())
                .setAccount(accountMapper.toDto(entity.getAccount()))
                .setSize(entity.getAttachment().length)
                .setFileName(entity.getFileName())
                .setContentType(entity.getFileType().getCode())
                .setStatementDate(entity.getReportDate())
                .setOpeningBalance(entity.getOpeningAmount())
                .setClosingBalance(entity.getClosingAmount());
        
                entity.getDetails()
                        .stream()
                        .map(this::toDto)
                        .forEach(dto::addToDetails);
        return dto;
    }

    public BankStatement toEntity(BankStatementDTO dto) {
        if (dto == null) {
            return null;
        }

        BankStatement entity = new BankStatement()
                .setReportDate(dto.getStatementDate())
                .setOpeningAmount(dto.getOpeningBalance())
                .setClosingAmount(dto.getClosingBalance())
                .setFileName(dto.getFileName())
                .setFileType(FileType.findByCode(dto.getContentType()));

        dto.getDetails().stream()
        .map(this::toEntity)
        .forEach(entity::addToDetails);

        return entity;
    }
    
    public BankStatementSummaryDTO toSummaryDto(BankStatement entity) {

    	BankStatementSummaryDTO dto = new BankStatementSummaryDTO()
        .setId(entity.getId())
        .setAccount(accountMapper.toDto(entity.getAccount()))
        .setFileName(entity.getFileName())
        .setReportDate(entity.getReportDate())
        .setItemsCount(entity.getDetails().size())
        .setTotalAmount(entity.getDetails().stream()
                .map(BankStatementDetail::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return dto;
    }

    private BankStatementDetailDTO toDto(BankStatementDetail entity) {
        BankStatementDetailDTO dto = new BankStatementDetailDTO();
        dto.setId(entity.getId())
        .setSequenceNumber(entity.getSequenceNumber())
        .setOperationDate(entity.getOperationDate())
        .setOperationType(entity.getOperationType().getCode())
        .setPostingDate(entity.getPostingDate())
        .setTitle(entity.getTitle())
        .setAmount(entity.getAmount())
        .setBalance(entity.getBalance());
        
        return dto;
    }

    private BankStatementDetail toEntity(BankStatementDetailDTO dto) {
        BankStatementDetail entity = new BankStatementDetail()
        .setId(dto.getId())
        .setSequenceNumber(dto.getSequenceNumber())
        .setOperationDate(dto.getOperationDate())
        .setOperationType(OperationType.valueOf(dto.getOperationType()))
        .setPostingDate(dto.getPostingDate())
        .setTitle(dto.getTitle())
        .setAmount(dto.getAmount())
        .setBalance(dto.getBalance())
        .setAccountNumber(dto.getAccountNumber())
        .setCounterparty(dto.getCounterparty())
        .setCounterpartyAddress(dto.getCounterpartyAddress());
        
        return entity;
    }
}