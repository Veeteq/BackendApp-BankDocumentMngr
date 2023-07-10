package com.veeteq.finance.bankdocument.service;

import com.veeteq.finance.bankdocument.dto.BankStatementInfoDTO;
import com.veeteq.finance.bankdocument.mapper.BankStatementMapper;
import com.veeteq.finance.bankdocument.repository.BankStatementDetailRepository;
import com.veeteq.finance.bankdocument.repository.UtilityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
public class BankStatementDetailService {

  private final BankStatementDetailRepository bankStatementDetailRepository;
  private final BankStatementMapper bankStatementMapper;

  @Autowired
  public BankStatementDetailService(BankStatementDetailRepository bankStatementDetailRepository,
                                    AccountService accountService,
                                    UtilityRepository utilityRepository) {
    this.bankStatementDetailRepository = bankStatementDetailRepository;
    this.bankStatementMapper = new BankStatementMapper(accountService, utilityRepository);
  }

  @Transactional
  public int setCounterpartyId(Long counterpartyId, Long id) {
    return bankStatementDetailRepository.setCounterpartyId(counterpartyId, id);
  }

  public List<BankStatementInfoDTO> findByOperationDate(LocalDate date) {
    List<BankStatementInfoDTO> result = bankStatementDetailRepository.findByOperationDate(date)
            .stream()
            .map(bankStatementMapper::toInfo)
            .collect(Collectors.toList());
    return result;
  }
}
