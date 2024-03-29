package com.veeteq.finance.bankdocument.controller;

import java.net.URI;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.veeteq.finance.bankdocument.dto.BankDataDTO;
import com.veeteq.finance.bankdocument.dto.BankStatementDTO;
import com.veeteq.finance.bankdocument.dto.BankStatementInfoDTO;
import com.veeteq.finance.bankdocument.dto.BankStatementSummaryDTO;
import com.veeteq.finance.bankdocument.dto.PageResponse;
import com.veeteq.finance.bankdocument.exception.ResourceNotFoundException;
import com.veeteq.finance.bankdocument.service.BankStatementDetailService;
import com.veeteq.finance.bankdocument.service.BankStatementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = BankStatementController.BASE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
@Tag(name = "BankStatementController", description = "API for Bank documents")
public class BankStatementController {
    public final static String BASE_URL = "/api/bank/documents";
    private final Logger LOG = LoggerFactory.getLogger(BankStatementController.class);

    private final BankStatementService bankStatementService;
    private final BankStatementDetailService bankStatementDetailService;

    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Autowired
    public BankStatementController(BankStatementService bankStatementService, BankStatementDetailService bankStatementDetailService) {
        this.bankStatementService = bankStatementService;
        this.bankStatementDetailService = bankStatementDetailService;
    }

    @Operation(summary = "Retrieve single BankStatement by its Id",
               description = "Get single BankStatement object by specifying its id. The response is BankStatementDTO object with all the required details.")
    @ApiResponses({@ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BankStatementDTO.class), mediaType = "application/json") }),
                   @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema()) }),
                   @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema()) }) })
    @GetMapping(path = "/{id}")
    public ResponseEntity<BankStatementDTO> getById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        LOG.info("Processing get request and retrieving single statment with id: " + id);

        BankStatementDTO bankStatement = bankStatementService.findById(id);

        return ResponseEntity.ok(bankStatement);
    }

    @GetMapping(path = "/{id}/counterparty")
    public ResponseEntity<List<BankDataDTO>> searchForCounterparties(@PathVariable("id") Long id) throws ResourceNotFoundException {
        LOG.info("Processing request to find couterparties for statment with id: " + id);

        List<BankDataDTO> bankData = bankStatementService.getBankDataById(id);
        bankStatementService.searchForCounterparty(bankData);

        return ResponseEntity.ok().build();
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

        /**
         * Register all bank statement details to message queue
         * before sending the response back to the caller
         */
        List<BankDataDTO> bankData = bankStatementService.getBankDataById(savedDocument.getId());
        bankStatementService.searchForCounterparty(bankData);

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

      LOG.info(MessageFormat.format("file {0} parsed successfully, number of details: {1}", file.getOriginalFilename(), parsed.getDetails().size()));

      return ResponseEntity.ok().body(parsed);
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BankStatementDTO> update(@PathVariable(name = "id", required = true) Long id,
                                                   @RequestPart(name = "document", required = true)  String json,
                                                   @RequestPart(name = "file",     required = false) MultipartFile file) {
        LOG.info("Processing put request and saving a new document");

        BankStatementDTO bankStatement = null;
        try {
            bankStatement = mapper.readValue(json, BankStatementDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Unable to read document");
        }

        BankStatementDTO updatedDocument = bankStatementService.updateDocument(id, bankStatement, file);

        return ResponseEntity.ok(updatedDocument);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws ResourceNotFoundException {
        LOG.info("Processing delete request of bank statement with id: " + id);

        bankStatementService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint for BankDocumentManager
     * Enable searching for BankStatementDetails by date
     * @param date
     * @return
     */
    @GetMapping(path = "/details")
    public ResponseEntity<List<BankStatementInfoDTO>> getDetails(@RequestParam(name = "date", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        LOG.info("Requesting bank statement details for: " + date);

        List<BankStatementInfoDTO> bankStatementDetails = bankStatementDetailService.findByOperationDate(date);
        return ResponseEntity.ok(bankStatementDetails);
    }
}
