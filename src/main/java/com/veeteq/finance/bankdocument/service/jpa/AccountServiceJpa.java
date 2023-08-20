package com.veeteq.finance.bankdocument.service.jpa;

import java.util.List;

import com.veeteq.finance.bankdocument.integration.homebudget.HomeBudgetMngrAPIClient;
import com.veeteq.finance.bankdocument.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.veeteq.finance.bankdocument.dto.AccountDTO;

@Service
public class AccountServiceJpa implements AccountService {
    private final Logger LOG = LoggerFactory.getLogger(AccountServiceJpa.class);

    private final HomeBudgetMngrAPIClient homeBudgetAPIClient;

    @Autowired
    public AccountServiceJpa(HomeBudgetMngrAPIClient homeBudgetAPIClient) {
        this.homeBudgetAPIClient = homeBudgetAPIClient;
    }

    @Override
    public List<AccountDTO> getAll() {
        List<AccountDTO> list = homeBudgetAPIClient.getAccounts();
        return list;
    }

    @Override
    @Cacheable(value = "accounts")
    public AccountDTO getById(Long id) {
        LOG.info("Requesting Account from external service with id: " + id);

        AccountDTO account = homeBudgetAPIClient.getAccountById(id);
        return account;
    }
    
}
