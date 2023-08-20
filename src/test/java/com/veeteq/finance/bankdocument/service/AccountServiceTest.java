package com.veeteq.finance.bankdocument.service;

import com.veeteq.finance.bankdocument.dto.AccountDTO;
import com.veeteq.finance.bankdocument.integration.homebudget.HomeBudgetMngrAPIClient;
import com.veeteq.finance.bankdocument.service.jpa.AccountServiceJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

class AccountServiceTest {

  private AccountServiceJpa accountService;

  @Mock
  private HomeBudgetMngrAPIClient apiClient;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    accountService = new AccountServiceJpa(apiClient);
  }

  @Test
  public void getById_NullResult() {
    AccountDTO account = accountService.getById(1L);
    assertNull(account);
  }

  @Test
  public void getById_SingleResult() {
    //given
    AccountDTO mockAccount = new AccountDTO().setId(1L).setName("Sample Account");

    Mockito.when(apiClient.getAccountById(anyLong())).thenReturn(mockAccount);

    //when
    AccountDTO account = accountService.getById(1L);

    //then
    assertNotNull(account);
    assertEquals(1, account.getId());
    assertEquals("Sample Account", account.getName());
    Mockito.verify(apiClient, Mockito.times(1)).getAccountById(anyLong());
  }

  @Test
  public void getAll_EmptyList() {
    List<AccountDTO> accounts = accountService.getAll();
    assertEquals(0, accounts.size());
  }
}