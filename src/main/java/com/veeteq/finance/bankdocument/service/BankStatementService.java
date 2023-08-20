package com.veeteq.finance.bankdocument.service;

import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.veeteq.finance.bankdocument.dto.BankDataDTO;
import com.veeteq.finance.bankdocument.dto.BankStatementDTO;
import com.veeteq.finance.bankdocument.dto.BankStatementSummaryDTO;
import com.veeteq.finance.bankdocument.dto.PageResponse;
import com.veeteq.finance.bankdocument.exception.ResourceNotFoundException;

public interface BankStatementService {

  BankStatementDTO saveDocument(BankStatementDTO bankStatementDto, MultipartFile file);

  BankStatementDTO updateDocument(Long id, BankStatementDTO bankStatementDto, MultipartFile file);

  BankStatementDTO parseDocument(Long accountId, MultipartFile file);

  BankStatementDTO findById(Long id);

  List<BankDataDTO> getBankDataById(Long id);

  PageResponse<BankStatementSummaryDTO> findAll(PageRequest pageRequest);

  void delete(Long id) throws ResourceNotFoundException;

  ResponseEntity<ByteArrayResource> findFileById(Long id);

  void searchForCounterparty(List<BankDataDTO> bankData);
}
