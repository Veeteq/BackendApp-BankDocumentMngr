package com.veeteq.finance.bankdocument.service.jpa;

import com.veeteq.finance.bankdocument.dto.BankStatementDTO;
import com.veeteq.finance.bankdocument.exception.ResourceNotFoundException;
import com.veeteq.finance.bankdocument.model.BankStatement;
import com.veeteq.finance.bankdocument.model.FileType;
import com.veeteq.finance.bankdocument.repository.BankStatementRepository;
import com.veeteq.finance.bankdocument.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankStatementServiceJpaTest {

  @Mock
  BankStatementRepository bankStatementRepository;

  @Mock
  AccountService accountService;

  @InjectMocks
  BankStatementServiceJpa service;

  @Test
  void findById_NoData_ThrowsException() {
    assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
  }

  @Test
  void findById_Success() {
    //given
    BankStatement data = new BankStatement()
            .setId(1L)
            .setFileName("sample data")
            .setAttachment(new Byte[0])
            .setFileType(FileType.APPLICATION_PDF);
    when(bankStatementRepository.findByIdWithDetails(anyLong())).thenReturn(Optional.of(data));

    //when
    BankStatementDTO result = service.findById(1L);

    //then
    assertNotNull(result);
    assertEquals(1, result.getId());
    assertEquals("sample data", result.getFileName());
    assertEquals(0, result.getSize());
    assertEquals("application/pdf", result.getContentType());
  }
}