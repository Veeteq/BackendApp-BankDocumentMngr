package com.veeteq.finance.bankdocument.controller;

import com.ctc.wstx.shaded.msv_core.util.Uri;
import com.veeteq.finance.bankdocument.dto.BankStatementDTO;
import com.veeteq.finance.bankdocument.service.BankStatementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BankStatementControllerTest {

  @Mock
  BankStatementService bankStatementService;

  @InjectMocks
  BankStatementController bankStatementController;

  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    mockMvc = MockMvcBuilders.standaloneSetup(bankStatementController).build();
  }

  @Test
  void getById() throws Exception {
    //given
    BankStatementDTO bankStatementDTO = new BankStatementDTO()
            .setId(1L)
            .setFileName("test file")
            .setStatementDate(LocalDate.of(2023,1,1))
            .setSize(100L);

    //when
    when(bankStatementService.findById(anyLong())).thenReturn(bankStatementDTO);

    //when
    String uri = new StringBuilder(BankStatementController.BASE_URL).append("/1").toString();
    mockMvc.perform(get(uri)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.fileName", equalTo("test file")));
  }
}