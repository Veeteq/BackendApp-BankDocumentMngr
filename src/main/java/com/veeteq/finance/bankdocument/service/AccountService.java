package com.veeteq.finance.bankdocument.service;

import com.veeteq.finance.bankdocument.dto.AccountDTO;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface AccountService {
  List<AccountDTO> getAll();

  @Cacheable(value = "accounts")
  AccountDTO getById(Long id);
}
