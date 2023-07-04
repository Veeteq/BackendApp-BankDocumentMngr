package com.veeteq.finance.bankdocument.service;

import java.util.List;

import com.veeteq.finance.bankdocument.integration.homebudget.HomeBudgetMngrAPIClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.veeteq.finance.bankdocument.dto.AccountDTO;

@Service
public class AccountService {
    private final Logger LOG = LoggerFactory.getLogger(AccountService.class);

    private final HomeBudgetMngrAPIClient homeBudgetAPIClient;

    @Autowired
    public AccountService(HomeBudgetMngrAPIClient homeBudgetAPIClient) {
        this.homeBudgetAPIClient = homeBudgetAPIClient;
    }

    public List<AccountDTO> getAll() {
        List<AccountDTO> list = homeBudgetAPIClient.getAccounts();
        return list;
    }

    public AccountDTO getById(Long id) {
        LOG.info("Requesting Account from external service with id: " + id);

        AccountDTO account = homeBudgetAPIClient.getAccountById(id);
        return account;
    }
    
}
