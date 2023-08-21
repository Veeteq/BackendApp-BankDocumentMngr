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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = AccountController.BASE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
@Tag(name = "AccountController", description = "API for Accounts, integrate with external Account provider")
public class AccountController {
    public final static String BASE_URL = "/api/accounts";
    private final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Retrieve list of Accounts",
               description = "Get full list of Account objects. The response is AccountDTO list.")
    @ApiResponses({@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = AccountDTO.class), mediaType = "application/json") }),
                   @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema()) }),
                   @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema()) }) })
    @GetMapping(path = {"", "/"})
    public ResponseEntity<List<AccountDTO>> getAccounts() {
        LOG.info("Retrieving accounts eligible for bank statement");

        List<AccountDTO> accounts = accountService.getAll();

        return ResponseEntity
                .ok()
                .body(accounts);
    }
}
