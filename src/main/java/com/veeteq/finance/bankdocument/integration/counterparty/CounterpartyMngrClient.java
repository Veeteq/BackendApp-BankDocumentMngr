package com.veeteq.finance.bankdocument.integration.counterparty;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.veeteq.finance.bankdocument.dto.BankStatementDetailDTO;

@FeignClient(name = "counterparty-manager")
public interface CounterpartyMngrClient {
    
    @PostMapping(path = "/api/counterparties/searchByBankData", consumes = MediaType.APPLICATION_JSON_VALUE)
    Long searchByBankData(@RequestBody BankStatementDetailDTO bankStatementDetail);

}
