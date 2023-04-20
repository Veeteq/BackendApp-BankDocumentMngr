package com.veeteq.finance.bankdocument.mapper;

import com.veeteq.finance.bankdocument.dto.AccountDTO;
import com.veeteq.finance.bankdocument.model.Account;

public class AccountMapper {

    public AccountDTO toDto(Account entity) {
        if (entity == null) {
            return null;
        }

        AccountDTO dto = new AccountDTO()
                .setId(entity.getId())
                .setName(entity.getName());
        
        return dto;
    }
    
    public Account toEntity(AccountDTO dto) {
        Account entity = new Account()
                .setId(dto.getId())
                .setName(dto.getName());
        
        return entity;
    }

}
