package com.veeteq.finance.bankdocument.service;

import java.util.List;

import com.veeteq.finance.bankdocument.integration.homebudget.HomeBudgetMngrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.veeteq.finance.bankdocument.dto.AccountDTO;

@Service
public class AccountService {

    private final HomeBudgetMngrClient homeBudgetMngrClient;

    @Autowired
    public AccountService(HomeBudgetMngrClient homeBudgetMngrClient) {
        this.homeBudgetMngrClient = homeBudgetMngrClient;
    }

    public List<AccountDTO> getAll() {
        List<AccountDTO> list = homeBudgetMngrClient.getAccounts();
        return list;
    }

    public AccountDTO getById(Long id) {
        AccountDTO account = homeBudgetMngrClient.getAccountById(id);
        return account;
    }
    
}
