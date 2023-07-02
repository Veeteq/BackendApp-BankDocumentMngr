package com.veeteq.finance.bankdocument.integration.homebudget;

import com.veeteq.finance.bankdocument.dto.AccountDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(url = "http://localhost:9194", name = "budget-manager")
public interface HomeBudgetMngrClient {

  @GetMapping(path = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
  @Cacheable(cacheNames = "accounts", key = "#id")
  List<AccountDTO> getAccounts();

  @GetMapping(path = "/api/accounts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Cacheable(cacheNames = "accounts", key = "#id")
  AccountDTO getAccountById(@PathVariable(value = "id") Long id);
}
