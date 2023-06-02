package com.veeteq.finance.bankdocument.service;

import com.veeteq.finance.bankdocument.repository.BankStatementDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BankStatementDetailService {

  private final BankStatementDetailRepository bankStatementDetailRepository;

  @Autowired
  public BankStatementDetailService(BankStatementDetailRepository bankStatementDetailRepository) {
    this.bankStatementDetailRepository = bankStatementDetailRepository;
  }

  @Transactional
  public int setCounterpartyId(Long counterpartyId, Long id) {
    return bankStatementDetailRepository.setCounterpartyId(counterpartyId, id);
  }
}
