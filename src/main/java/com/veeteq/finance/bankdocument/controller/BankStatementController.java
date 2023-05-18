package com.veeteq.finance.bankdocument.controller;

import java.net.URI;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.veeteq.finance.bankdocument.dto.BankStatementDTO;
import com.veeteq.finance.bankdocument.dto.BankStatementSummaryDTO;
import com.veeteq.finance.bankdocument.dto.PageResponse;
import com.veeteq.finance.bankdocument.exception.ResourceNotFoundException;
import com.veeteq.finance.bankdocument.integration.counterparty.CounterpartyMngrClient;
import com.veeteq.finance.bankdocument.service.BankStatementService;

@RestController
@RequestMapping(path = "/api/bank/documents", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class BankStatementController {
    private final Logger LOG = LoggerFactory.getLogger(BankStatementController.class);

    private final BankStatementService bankStatementService;
    private final CounterpartyMngrClient counterpartyClient;
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    
    @Autowired
    public BankStatementController(BankStatementService bankStatementService,
                                   CounterpartyMngrClient counterpartyMngrClient) {
        this.bankStatementService = bankStatementService;
        this.counterpartyClient = counterpartyMngrClient;
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<BankStatementDTO> getById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        LOG.info("Processing get request and retrieving single statment with id: " + id);
        
        BankStatementDTO bankStatement = bankStatementService.findById(id);
        Long counterpartyId = counterpartyClient.searchByBankData(bankStatement.getDetails().get(0));
        System.out.println("counterpartyId: " + counterpartyId);
        
        return ResponseEntity.ok(bankStatement);
    }
    
    @GetMapping(path = "/{id}/file")
    public ResponseEntity<ByteArrayResource> findFileById(@PathVariable(name = "id", required = true) Long id) {
        LOG.info("Processing get request and retrieving file attachement for statment with id: " + id);
        
        ResponseEntity<ByteArrayResource> response = bankStatementService.findFileById(id);
        
        return response;
    }
        
    @GetMapping(path = "/summary", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getSummary(@RequestParam(name = "page",   defaultValue = "0") int page,
                                        @RequestParam(name = "size",   defaultValue = "10") int size,
                                        @RequestParam(name = "column", defaultValue = "id") String column,
                                        @RequestParam(name = "dir",    defaultValue = "ASC") String dir) {
        LOG.info("Processing getSummary request: page=" + page + ", size=" + size + ", column: " + column + ", dir: " + dir);
        
        Sort.Direction sortDir = dir.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort.Order order = new Sort.Order(sortDir, column).ignoreCase();
        Sort sort = Sort.by(order);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        PageResponse<BankStatementSummaryDTO> response = bankStatementService.findAll(pageRequest);

        return ResponseEntity.ok(response);
    }
    
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BankStatementDTO> save(@RequestPart(name = "document", required = true)  String json, 
                                                 @RequestPart(name = "file",     required = false) MultipartFile file) {
        LOG.info("Processing post request and saving a new document");

        BankStatementDTO bankStatement = null;

        try {
            bankStatement = mapper.readValue(json, BankStatementDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Unable to read document");
        }

        BankStatementDTO savedDocument = bankStatementService.saveDocument(bankStatement, file);

        LOG.info(MessageFormat.format("item with id {0} created", savedDocument.getId()));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedDocument.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedDocument);
    }
    
    @PostMapping(path = "/parse", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<BankStatementDTO> parse(@RequestParam(name = "accountId", required = true) Long accountId, 
                                                  @RequestPart(name = "file",       required = true) MultipartFile file) {
      LOG.info("Processing post request and parsing a new document of: " + file.getContentType());

      BankStatementDTO parsed = bankStatementService.parseDocument(accountId, file);       
      
      LOG.info(MessageFormat.format("item with id {0} created", parsed.getId()));
      
      return ResponseEntity.ok().body(parsed);
    }    
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws ResourceNotFoundException {
        LOG.info("Processing delete request of bank statement with id: " + id);

        bankStatementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
