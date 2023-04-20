package com.veeteq.finance.bankdocument.fileupload;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.veeteq.finance.bankdocument.dto.BankStatementDTO;
import com.veeteq.finance.bankdocument.dto.BankStatementDetailDTO;
import com.veeteq.finance.bankdocument.model.OperationType;
import com.veeteq.finance.bankdocument.util.DateUtil;
import com.veeteq.finance.bankdocument.util.NumberUtil;

public class CitiBankProcessor implements UploadProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(CitiBankProcessor.class);
    
    private final Pattern datePattern = Pattern.compile("([1-9]|0[1-9]|[12][0-9]|3[01])[.](0[1-9]|1[012])[.](19|20)\\d\\d");
    private final Pattern valuePattern = Pattern.compile("(­)?(\\s)?(\\d{0,3}\\.)*\\d{1,3},\\d{2}(\\s)?zł");
    
    @Override
    public BankStatementDTO process(InputStream inputStream) {
        LOG.info("Start processing CitiBank statment");

        BankStatementDTO bankStatement = new BankStatementDTO();

        try {
            PDDocument document = PDDocument.load(inputStream);
            PDPageTree pages = document.getDocumentCatalog().getPages();
            
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            
            for (int i = 1; i <= pages.getCount(); i++) {
                stripper.setStartPage(i);
                stripper.setEndPage(i);
                String documentContent = stripper.getText(document);
                            
                parseDocumentContent(documentContent, bankStatement);
            }
            
            document.close();
            inputStream.close();
            
            updateBalance(bankStatement);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        String msg = MessageFormat.format("End processing CitiBank statment. {0, number} items processed.", bankStatement.getDetails().size());
        LOG.info(msg);
        return bankStatement;
    }

    private void parseDocumentContent(String documentContent, BankStatementDTO bankStatement) {        
        String lines[] = documentContent.split("\\r?\\n");
        boolean hasTransactions = false;
        AtomicInteger seq = new AtomicInteger(1);
        
        String line = null;
        int idx = 0;
        while (idx < lines.length) {
            line = lines[idx++];
            
            if (line.contains("Data wyciągu")) {
                Matcher dateMatcher = datePattern.matcher(line);
                if (dateMatcher.find()) {
                    String dateTxt = dateMatcher.group();
                    LocalDate localDate = DateUtil.parse(dateTxt);
                    bankStatement.setStatementDate(localDate);
                }
            }
            if (line.contains("Saldo zadłużenia")) {
                BigDecimal finalAmount = null;
                Matcher valueMatcher = valuePattern.matcher(line);
                while(valueMatcher.find()) {
                    String amountTxt = valueMatcher.group();
                    finalAmount = NumberUtil.convertTo(amountTxt);
                }
                bankStatement.setClosingBalance(finalAmount);
            }
            
            if (line.contains("Poprzednie saldo zadłużenia")) {
                BigDecimal openingAmount = null;
                Matcher valueMatcher = valuePattern.matcher(line);
                while(valueMatcher.find()) {
                    String amountTxt = valueMatcher.group();
                    openingAmount = NumberUtil.convertTo(amountTxt);
                }
                bankStatement.setOpeningBalance(openingAmount);
            }
            
            if (line.contains("Data transakcji Opis transakcji Kwota transakcji")) {
                hasTransactions = true;
                line = lines[idx++]; //Move cursor to the next line
                break;
            }
        }
        
        /* Execute only if there are transactions in the bank statement */
        if (hasTransactions) {
            
            BankStatementDetailDTO statementDetail = null;
            while((statementDetail = parseLine(line)) != null) {
                System.out.println(line);
                
                if(statementDetail.getTitle().contains("SPŁATA")) {
                    statementDetail.setOperationType(OperationType.PRZELEW_ZEWNETRZNY_PRZYCHODZACY.name());
                } else {
                    statementDetail.setOperationType(OperationType.ZAKUP_PRZY_UZYCIU_KARTY.name());
                }
                
                statementDetail.setSequenceNumber(seq.getAndIncrement());
                bankStatement.addToDetails(statementDetail);
                line = lines[idx++];
            }
        }
    }
    
    private BankStatementDetailDTO parseLine(String line) {
        if(line == null) {
            return null;
        }

        if (line.contains("Saldo zadłużenia")) {
            return null;
        }

        if (line.startsWith("str.")) {
            return null;
        }
        
        BankStatementDetailDTO statementDetail = new BankStatementDetailDTO();
        
        Matcher dateMatcher = datePattern.matcher(line);
        if (dateMatcher.find()) {
            String dateTxt = dateMatcher.group();
            LocalDate localDate = DateUtil.parse(dateTxt);
            statementDetail.setOperationDate(localDate);
            statementDetail.setPostingDate(localDate);

            line = line.replace(dateTxt, "");
        } else {
            //No date found in statement line
            return null;
        }

        Matcher valueMatcher = valuePattern.matcher(line);
        while(valueMatcher.find()) {
            String amountTxt = valueMatcher.group();
            BigDecimal bd = NumberUtil.convertTo(amountTxt);
            statementDetail.setAmount(bd.negate());
            line = line.replace(amountTxt, "");
        }  

        statementDetail.setTitle(line.trim());
        
        return statementDetail;
    }    
    
    private void updateBalance(BankStatementDTO bankStatement) {
        AtomicReference<BigDecimal> balance = new AtomicReference<>(bankStatement.getClosingBalance().negate());
        
        bankStatement.getDetails()
        .stream()
        .sorted((a, b) -> b.getSequenceNumber() - a.getSequenceNumber())
        .forEach(entry -> {
          LOG.info("set balance to: " + balance.get());
          entry.setBalance(balance.getAndUpdate(b -> b.subtract(entry.getAmount())));                
        });
    }      
}
