package com.veeteq.finance.bankdocument.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.veeteq.finance.bankdocument.dto.AccountDTO;
import com.veeteq.finance.bankdocument.mapper.AccountMapper;
import com.veeteq.finance.bankdocument.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper mapper = new AccountMapper();

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountDTO> getAll() {
        
    	Sort defaultSort = Sort.by(Order.asc("id"));
        List<AccountDTO> list = accountRepository.findAll(defaultSort)
        .stream()
        .map(mapper::toDto)
        .collect(Collectors.toList());
        
        return list;
    }
    
}
