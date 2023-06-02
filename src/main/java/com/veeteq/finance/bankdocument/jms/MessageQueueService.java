package com.veeteq.finance.bankdocument.jms;

import static com.veeteq.finance.bankdocument.jms.JmsConfiguration.COUNTERPARTY_QUEUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.veeteq.finance.bankdocument.dto.BankStatementDTO;

@Service
public class MessageQueueService {

    private final JmsTemplate jmsTemplate;
    
    @Autowired
    public MessageQueueService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void registerBankStatement(BankStatementDTO bankStatement) {        
        System.out.println("the total amount of details is: " + bankStatement.getDetails().size());
        bankStatement.getDetails().stream()
                .filter(detail -> detail.getCounterpartyId() == null)
                //.filter(detail -> detail.getCounterpartyIban() != null)
                .forEach(detail -> {
            System.out.println("registering bank statement detail: " + detail.getTitle());

            //jmsTemplate.send(COUNTERPARTY_QUEUE, session -> {
            //    BytesMessage message = session.createBytesMessage();
            //    message.writeBytes(detail);
            //    return message;
            //});

            jmsTemplate.convertAndSend(COUNTERPARTY_QUEUE, detail, message -> {
                return message;
            });
        });
    }

}
