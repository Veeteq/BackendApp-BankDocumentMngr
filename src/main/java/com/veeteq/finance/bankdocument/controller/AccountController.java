package com.veeteq.finance.bankdocument.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.veeteq.finance.bankdocument.dto.AccountDTO;
import com.veeteq.finance.bankdocument.service.AccountService;

@RestController
@RequestMapping(path = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class AccountController {
    private final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;
    
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping(path = {"", "/"})
    public ResponseEntity<List<AccountDTO>> getAccounts() {
        LOG.info("Retrieving accounts eligible for bank statement");

        List<AccountDTO> accounts = accountService.getAll();
        
        return ResponseEntity
                .ok()
                .body(accounts);
    }
}
