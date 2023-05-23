package com.veeteq.finance.bankdocument.jms;


import static com.veeteq.finance.bankdocument.jms.JmsConfiguration.COUNTERPARTY_QUEUE;

import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.veeteq.finance.bankdocument.dto.BankStatementDetailDTO;
import com.veeteq.finance.bankdocument.integration.counterparty.CounterpartyMngrClient;

@Component
public class MessageQueueReceiver {
    private final static Logger LOG = LoggerFactory.getLogger(MessageQueueReceiver.class);

    private final CounterpartyMngrClient counterpartyClient;
    
    @Autowired
    public MessageQueueReceiver(CounterpartyMngrClient counterpartyClient) {
        this.counterpartyClient = counterpartyClient;
    }


    @JmsListener(destination = COUNTERPARTY_QUEUE)
    public void receiveMessage(@Payload BankStatementDetailDTO detail, @Headers MessageHeaders headers, Message<BankStatementDetailDTO> message, Session session) {
        LOG.info("received <" + detail.getTitle() + ">");
        LOG.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        LOG.info("######          Message Details           #####");
        LOG.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        LOG.info("headers: " + headers);
        LOG.info("message: " + message);
        LOG.info("session: " + session);
        LOG.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        
        Long counterpartyId = counterpartyClient.searchByBankData(detail);
        System.out.println("counterpartyId: " + counterpartyId);
    }
    
}