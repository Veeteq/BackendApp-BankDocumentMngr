package com.veeteq.finance.bankdocument.service.jpa;

import com.veeteq.finance.bankdocument.dto.AccountDTO;
import com.veeteq.finance.bankdocument.dto.BankStatementDTO;
import com.veeteq.finance.bankdocument.model.BankStatement;
import com.veeteq.finance.bankdocument.repository.BankStatementRepository;
import com.veeteq.finance.bankdocument.repository.UtilityRepository;
import com.veeteq.finance.bankdocument.repository.UtilityRepositoryDefault;
import com.veeteq.finance.bankdocument.service.AccountService;
import com.veeteq.finance.bankdocument.service.BankStatementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BankStatementServiceTest {

  AccountDTO accountDto = new AccountDTO().setId(1L).setName("Test Account");

  @Autowired
  BankStatementRepository bankStatementRepository;

  @Autowired
  EntityManager entityManager;

  UtilityRepository utilityRepository;

  @Mock
  AccountService accountService;

  BankStatementService bankStatementService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    utilityRepository = new UtilityRepositoryDefault(entityManager);
    bankStatementService = new BankStatementServiceJpa(bankStatementRepository, null, accountService, utilityRepository);
  }

  @Test
  public void testSaveBankStatement() {
    //given
    BankStatementDTO bankStatement = new BankStatementDTO()
            .setId(1L)
            .setAccount(accountDto)
            .setContentType("text/html");
    MultipartFile multipartFile = new MockMultipartFile("bankDocument", "bankDocument.txt", "text/plain", "Whatever".getBytes());

    //when
    when(accountService.getById(anyLong())).thenReturn(accountDto);
    BankStatementDTO savedDocument = bankStatementService.saveDocument(bankStatement, multipartFile);

    //then
    assertNotNull(savedDocument);
  }
}
