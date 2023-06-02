package com.veeteq.finance.bankdocument.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.veeteq.finance.bankdocument.dto.BankStatementDTO;
import com.veeteq.finance.bankdocument.dto.BankStatementSummaryDTO;
import com.veeteq.finance.bankdocument.dto.PageResponse;
import com.veeteq.finance.bankdocument.exception.ResourceNotFoundException;
import com.veeteq.finance.bankdocument.fileupload.UploadProcessor;
import com.veeteq.finance.bankdocument.fileupload.UploadProcessorFactory;
import com.veeteq.finance.bankdocument.jms.MessageQueueService;
import com.veeteq.finance.bankdocument.mapper.AccountMapper;
import com.veeteq.finance.bankdocument.mapper.BankStatementMapper;
import com.veeteq.finance.bankdocument.model.Account;
import com.veeteq.finance.bankdocument.model.BankStatement;
import com.veeteq.finance.bankdocument.repository.AccountRepository;
import com.veeteq.finance.bankdocument.repository.BankStatementRepository;
import com.veeteq.finance.bankdocument.repository.UtilityRepository;

@Service
public class BankStatementService {
    private final Logger LOG = LoggerFactory.getLogger(BankStatementService.class);
    
    private final AccountRepository accountRepository;
    private final BankStatementRepository bankStatementRepository;
    private final AccountMapper accountMapper;
    private final BankStatementMapper mapper;
    private final MessageQueueService messageQueueService;
    
    @Autowired
    public BankStatementService(AccountRepository accountRepository, 
                                BankStatementRepository bankStatementRepository,
                                MessageQueueService messageQueueService,
                                UtilityRepository utilityRepository) {
        this.accountRepository = accountRepository;
        this.bankStatementRepository = bankStatementRepository;
        this.messageQueueService = messageQueueService;

        this.mapper = new BankStatementMapper(utilityRepository);
        this.accountMapper = new AccountMapper();
    }

    @Transactional
    public BankStatementDTO saveDocument(BankStatementDTO bankStatementDto, MultipartFile file) {
        LOG.info("saveBankStatement");
        
        Account accountRef = accountRepository.getReferenceById(bankStatementDto.getAccount().getId());
        
        BankStatement bankStatement = mapper.toEntity(bankStatementDto);
        bankStatement.setAccount(accountRef);
        bankStatement.setAttachment(fileToBytes(file));
        
        BankStatement savedBankStatement = bankStatementRepository.save(bankStatement);
        
        BankStatementDTO response = mapper.toDto(savedBankStatement);

        /**
         * Register all bank statement details to message queue
         * before sending the response back to the caller
         */
        messageQueueService.registerBankStatement(response);

        return response;
    }
    
    public BankStatementDTO parseDocument(Long accountId, MultipartFile document) {
        Account accountRef = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for this id :: " + accountId));
        
        UploadProcessor processor = UploadProcessorFactory.getUploadProcessor(document.getContentType());

        BankStatementDTO bankStatement = null;

        try {
            bankStatement = processor.process(document.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        bankStatement
        .setAccount(accountMapper.toDto(accountRef))
        .setSize(document.getSize())
        .setContentType(document.getContentType())
        .setFileName(document.getOriginalFilename());

        return bankStatement;
    }

    public BankStatementDTO findById(Long id) {
        LOG.info("Retrieving bank statement with id: " + id);
        
        BankStatement savedBankStatement = bankStatementRepository
        .findByIdWithDetails(id)
        .orElseThrow(() -> new ResourceNotFoundException("Bank Statement not found for this id :: " + id));

        BankStatementDTO response = mapper.toDto(savedBankStatement);

        return response;
    }
        
    public PageResponse<BankStatementSummaryDTO> findAll(PageRequest pageRequest) {
        LOG.info("getSummary: page=" + pageRequest.getPageNumber() + ", size=" + pageRequest.getPageSize() + ", sort=[" + pageRequest.getSort().toString() + ']');
        
        Page<BankStatement> page = bankStatementRepository.findAllWithAccountAndDetails(pageRequest);
        List<BankStatementSummaryDTO> content = page.getContent()
                .stream()
                .map(mapper::toSummaryDto)
                .collect(Collectors.toList());
        
        PageResponse<BankStatementSummaryDTO> response = new PageResponse<BankStatementSummaryDTO>()
                .setContent(content)
                .setPageNo(page.getNumber())
                .setPageSize(page.getSize())
                .setTotalElements(page.getTotalElements())
                .setTotalPages(page.getTotalPages())
                .setLast(page.isLast());

        return response;
    }

    public void delete(Long id) throws ResourceNotFoundException {
        LOG.info("Deleting bank statement with id: " + id);
        
        BankStatement savedBankStatement = bankStatementRepository.getReferenceById(id);
        
        bankStatementRepository.delete(savedBankStatement);
    }

    public ResponseEntity<ByteArrayResource> findFileById(Long id) {
        LOG.info("Retrieving file attachement for bank statement with id: " + id);
        
        BankStatement savedBankStatement = bankStatementRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bank Statement not found for this id :: " + id));
        
        Byte[] attachment = savedBankStatement.getAttachment();
        ByteArrayResource resource = new ByteArrayResource(toByteArray(attachment), savedBankStatement.getFileName());
        
        return ResponseEntity
                .ok()
                .contentLength(resource.contentLength())
                .contentType(savedBankStatement.getFileType().getMediaType())
                .body(resource);              
    }

    public void searchForCounterparty(BankStatementDTO bankStatement) {
        /**
         * Register all bank statement details to message queue
         */        
        messageQueueService.registerBankStatement(bankStatement);        
    }

    private byte[] toByteArray(Byte[] objBytes) {
        byte[] bytes = new byte[objBytes.length];
        for(int i = 0; i < objBytes.length; i++){
            bytes[i] = objBytes[i];
        }
        return bytes;
    }

    private Byte[] fileToBytes(MultipartFile file) {
        byte[] bytes;
        try {
            bytes = file.getBytes();
            Byte[] bytesObject = new Byte[bytes.length];
            int i = 0;
            for(Byte b : bytes) {
                bytesObject[i++] = Byte.valueOf(b);
            }
            return bytesObject;
        } catch (IOException e) {
            return new Byte[0];
        }
    }
}
