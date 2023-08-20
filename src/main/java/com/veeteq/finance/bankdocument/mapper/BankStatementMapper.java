package com.veeteq.finance.bankdocument.mapper;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import com.veeteq.finance.bankdocument.dto.*;
import com.veeteq.finance.bankdocument.dto.BankStatementInfoDTO;
import com.veeteq.finance.bankdocument.model.BankStatement;
import com.veeteq.finance.bankdocument.model.BankStatementDetail;
import com.veeteq.finance.bankdocument.model.BankStatementDetailId;
import com.veeteq.finance.bankdocument.model.FileType;
import com.veeteq.finance.bankdocument.model.OperationType;
import com.veeteq.finance.bankdocument.repository.UtilityRepository;
import com.veeteq.finance.bankdocument.service.AccountService;

public class BankStatementMapper {
    private final AccountService accountService;
    private final UtilityRepository utilityRepository;

    public BankStatementMapper(AccountService accountService, UtilityRepository utilityRepository) {
        this.accountService = accountService;
        this.utilityRepository = utilityRepository;
    }

    public BankStatementDTO toDto(BankStatement entity) {
        if (entity == null) {
            return null;
        }
        AccountDTO account = accountService.getById(entity.getAccountId());
        BankStatementDTO dto = new BankStatementDTO()
                .setId(entity.getId())
                .setAccount(account)
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
                .setId(dto.getId() != null ? dto.getId() : utilityRepository.getBankStatementId())
                .setReportDate(dto.getStatementDate())
                .setOpeningAmount(dto.getOpeningBalance())
                .setClosingAmount(dto.getClosingBalance())
                .setFileName(dto.getFileName())
                .setFileType(FileType.findByCode(dto.getContentType()));

        Long[] detailIds = utilityRepository.getBankStatementDetailId(dto.getDetails().size());
        AtomicInteger idx = new AtomicInteger(0);

        dto.getDetails().stream()
        .map(this::toEntity)
        .forEach(detail -> {
            detail.setDetailId(detail.getDetailId() == null ? detailIds[idx.getAndIncrement()] : detail.getDetailId());
            entity.addToDetails(detail);
        });

        return entity;
    }

    public BankStatement updateWithDto(BankStatement entity, BankStatementDTO dto) {
        entity.setReportDate(dto.getStatementDate())
                .setOpeningAmount(dto.getOpeningBalance())
                .setClosingAmount(dto.getClosingBalance())
                .setFileName(dto.getFileName())
                .setFileType(FileType.findByCode(dto.getContentType()));

        Long[] detailIds = utilityRepository.getBankStatementDetailId(dto.getDetails().size());
        AtomicInteger idx = new AtomicInteger(0);

        dto.getDetails().stream()
                .map(this::toEntity)
                .forEach(detail -> {
                    int index = entity.getDetails().indexOf(detail);
                    if (index < 0) {
                        detail.setDetailId(detail.getDetailId() == null ? detailIds[idx.getAndIncrement()] : detail.getDetailId());
                        entity.addToDetails(detail);

                    } else {
                        entity.getDetails().get(index);
                    }

                });

        return entity;
    }

    public BankStatementSummaryDTO toSummaryDto(BankStatement entity) {

        AccountDTO account = accountService.getById(entity.getAccountId());
        BankStatementSummaryDTO dto = new BankStatementSummaryDTO()
                .setId(entity.getId())
                .setAccount(account)
                .setFileName(entity.getFileName())
                .setReportDate(entity.getReportDate())
                .setItemsCount(entity.getDetails().size())
                .setTotalAmount(entity.getDetails().stream()
                        .map(BankStatementDetail::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));

        return dto;
    }

    public BankStatementDetailDTO toDto(BankStatementDetail entity) {
        BankStatementDetailId bankStatementDetailId = entity.getId();

        BankStatementDetailDTO dto = new BankStatementDetailDTO()
                .setBankStatementId(bankStatementDetailId.getBankStatementId())
                .setSequenceNumber(bankStatementDetailId.getSequenceNumber())
                .setDetailId(entity.getDetailId())
                .setOperationDate(entity.getOperationDate())
                .setOperationType(entity.getOperationType().getCode())
                .setPostingDate(entity.getPostingDate())
                .setTitle(entity.getTitle())
                .setAmount(entity.getAmount())
                .setBalance(entity.getBalance())
                .setCounterpartyId(entity.getCounterpartyId())
                .setCounterpartyName(entity.getCounterpartyName())
                .setCounterpartyAddress(entity.getCounterpartyAddress())
                .setCounterpartyIban(entity.getCounterpartyIban());

        return dto;
    }

    private BankStatementDetail toEntity(BankStatementDetailDTO dto) {
        BankStatementDetail entity = new BankStatementDetail()
        .setId(new BankStatementDetailId(dto.getBankStatementId(), dto.getSequenceNumber()))
        .setDetailId(dto.getDetailId())
        .setOperationDate(dto.getOperationDate())
        .setOperationType(OperationType.valueOf(dto.getOperationType()))
        .setPostingDate(dto.getPostingDate())
        .setTitle(dto.getTitle())
        .setAmount(dto.getAmount())
        .setBalance(dto.getBalance())
        .setCounterpartyId(dto.getCounterpartyId())
        .setCounterpartyName(dto.getCounterpartyName())
        .setCounterpartyAddress(dto.getCounterpartyAddress())
        .setCounterpartyIban(dto.getCounterpartyIban());
        
        return entity;
    }

    public BankDataDTO toBankData(BankStatementDetail entity) {
      if (entity == null) {
        return null;
      }

      BankDataDTO bankData = new BankDataDTO()
                .setId(entity.getDetailId())
                .setIban(entity.getCounterpartyIban())
                .setCounterparty(entity.getCounterpartyName())
                .setCounterpartyAddress(entity.getCounterpartyAddress())
                .setTitle(entity.getTitle());
      return bankData;
    }

    public BankStatementInfoDTO toInfo(BankStatementDetail entity) {
        if (entity == null) {
            return null;
        }
        BankStatementDetailId bankStatementDetailId = entity.getId();
        BankStatementInfoDTO dto = new BankStatementInfoDTO()
                .setDetailId(entity.getDetailId())
                .setSequenceNumber(bankStatementDetailId.getSequenceNumber())
                .setOperationType(entity.getOperationType().getCode())
                .setOperationDate(entity.getOperationDate())
                .setTitle(entity.getTitle())
                .setAccountId(entity.getBankStatement().getAccountId())
                .setCounterpartyId(entity.getCounterpartyId())
                .setAmount(entity.getAmount());
        return dto;
    }

}
