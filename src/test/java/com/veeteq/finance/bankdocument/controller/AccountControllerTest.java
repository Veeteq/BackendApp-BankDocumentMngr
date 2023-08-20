package com.veeteq.finance.bankdocument.controller;

import com.veeteq.finance.bankdocument.dto.AccountDTO;
import com.veeteq.finance.bankdocument.service.jpa.AccountServiceJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

class AccountControllerTest {

  private AccountController controller;

  @Mock
  private AccountServiceJpa accountService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    controller = new AccountController(accountService);
  }

  @Test
  public void getAccounts() throws Exception {
    //given
    AccountDTO account1 = new AccountDTO().setId(1L).setName("Sample Account 1");
    AccountDTO account2 = new AccountDTO().setId(2L).setName("Sample Account 2");
    List<AccountDTO> sampleAccounts = List.of(account1, account2);
    Mockito.when(accountService.getAll()).thenReturn(sampleAccounts);

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    //when - then
    mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts"))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(content().string(containsString("Sample Account")));;
  }

}