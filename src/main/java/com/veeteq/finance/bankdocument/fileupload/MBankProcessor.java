package com.veeteq.finance.bankdocument.fileupload;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.veeteq.finance.bankdocument.dto.BankStatementDTO;
import com.veeteq.finance.bankdocument.dto.BankStatementDetailDTO;
import com.veeteq.finance.bankdocument.model.OperationType;
import com.veeteq.finance.bankdocument.util.DateUtil;
import com.veeteq.finance.bankdocument.util.NumberUtil;

public class MBankProcessor implements UploadProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(MBankProcessor.class.getSimpleName());
        
    @Override
    public BankStatementDTO process(InputStream inputStream) {
        
        BankStatementDTO bankStatement = new BankStatementDTO();
        
        
        try {
            Document document = Jsoup.parse(inputStream, "UTF-8", "https://online.mbank.pl");
            LOG.info(document.title());
            
            Elements tables = document.select("table");
            LOG.info("found: " + tables.size() + " tables.");
            
            Element table = tables.get(5);
            Elements tableRows = table.select("tr");
            
            AtomicInteger idx = new AtomicInteger(1);
            
            tableRows.stream()
            .skip(3)
            .map(row -> parseRecord(row))
            .filter(detail -> detail != null)
            .peek(detail -> detail.setSequenceNumber(idx.getAndIncrement()))
            .forEach(bankStatement::addToDetails);
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return bankStatement;
    }

    private BankStatementDetailDTO parseRecord(Element row) {
        BankStatementDetailDTO entry = new BankStatementDetailDTO();
        Elements cells = row.select("td");
        if (cells.size() == 5) {
            LocalDate operationDate = DateUtil.parse(cells.get(0).text());
            LocalDate postingDate = DateUtil.parse(cells.get(1).text());
            OperationDetails operationDetails = parseOperationDetails(cells.get(2));
            BigDecimal operationAmount = NumberUtil.convertTo(cells.get(3).text(), ',', ' ', "-");
            BigDecimal balance = NumberUtil.convertTo(cells.get(4).text(), ',', ' ', "-");
            
            entry.setOperationDate(operationDetails.getOperationDate() != null ? operationDetails.getOperationDate() : operationDate)
            .setPostingDate(postingDate)
            .setOperationType(OperationType.findByCode(operationDetails.getOperationType()).name())
            .setTitle(operationDetails.getTitle())
            .setCounterpartyIban(operationDetails.getAccountNumber())
            .setCounterpartyName(operationDetails.getCounterparty())
            .setCounterpartyAddress(operationDetails.getCounterpartyAddress())
            .setAmount(operationAmount)
            .setBalance(balance);
            
            return entry;
        }
        
        return null;
    }

    private OperationDetails parseOperationDetails(Element cell) {
        OperationDetails operationDetails = new OperationDetails();
        operationDetails.setOperationType(cell.textNodes().get(0).text());
        
        int size = cell.textNodes().size();
        switch(size) {
        case 5:            
            operationDetails.setCounterparty(cell.textNodes().get(1).text());
            operationDetails.setCounterpartyAddress(cell.textNodes().get(2).text());
            operationDetails.setAccountNumber(cell.textNodes().get(3).text());
            operationDetails.setTitle(cell.textNodes().get(4).text());
            break;
        case 4:
            operationDetails.setCounterparty(cell.textNodes().get(1).text());
            operationDetails.setAccountNumber(cell.textNodes().get(2).text());
            operationDetails.setTitle(cell.textNodes().get(3).text());
            break;
        case 2:
            operationDetails.setTitle(cell.textNodes().get(1).text());
            break;
        case 1:
            break;
        default:
            throw new UnsupportedOperationException("Unexpected number of text nodes: " + size + ", " + cell.text());
        }
                
        return operationDetails;
    }

    private static class OperationDetails {
        private String operationType;
        private String title;
        private String counterparty;
        private String counterpartyAddress;
        private String accountNumber;
        private LocalDate operationDate;        
        
        
        public String getOperationType() {
            return operationType;
        }

        public OperationDetails setOperationType(String operationType) {
            this.operationType = operationType;
            return this;
        }
        
        public String getTitle() {
            return title;
        }

        public OperationDetails setTitle(String title) {
            if (this.operationType.equals("ZAKUP PRZY UŻYCIU KARTY")) {
                String[] split = title.split("DATA TRANSAKCJI:");
                if (split.length == 1) {
                    this.title = split[0].trim();
                } else if (split.length == 2) {
                    this.title = split[0].trim();
                    this.operationDate = DateUtil.parse(split[1].trim());
                }
                
            } else {
                this.title = String.join(" ", title.split("\\s+"));
            }
            return this;
        }
        
        public String getCounterparty() {
            return counterparty;
        }

        public OperationDetails setCounterparty(String counterparty) {
            this.counterparty = counterparty;
            return this;
        }

        public String getCounterpartyAddress() {
            return counterpartyAddress;
        }

        public OperationDetails setCounterpartyAddress(String counterpartyAddress) {
            this.counterpartyAddress = counterpartyAddress;
            return this;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public OperationDetails setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public LocalDate getOperationDate() {
            return operationDate;
        }

        @Override
        public String toString() {
            return "OperationDetails [operationType=" + operationType + ", title=" + title + ", counterparty=" + counterparty + ", counterpartyAddress=" + counterpartyAddress + ", accountNumber=" + accountNumber + ", operationDate=" + operationDate + "]";
        }

    }
}
