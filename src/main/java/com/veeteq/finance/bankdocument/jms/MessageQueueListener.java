package com.veeteq.finance.bankdocument.jms;

import static com.veeteq.finance.bankdocument.jms.JmsConfiguration.COUNTERPARTY_RESPONSE_QUEUE;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.veeteq.finance.bankdocument.service.BankStatementDetailService;

@Component
public class MessageQueueListener {
    private final static Logger LOG = LoggerFactory.getLogger(MessageQueueListener.class);

    private final BankStatementDetailService bankStatementDetailService;

    @Autowired
    public MessageQueueListener(BankStatementDetailService bankStatementDetailService) {
        this.bankStatementDetailService = bankStatementDetailService;
    }

    @JmsListener(destination = COUNTERPARTY_RESPONSE_QUEUE)
    public void receiveMessage(@Payload Map<String, Object> payload, @Headers MessageHeaders headers, Message message, Session session) {
        LOG.info("received <" + payload + ">");
        LOG.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        LOG.info("######          Message Details           #####");
        LOG.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        LOG.info("headers: " + headers);
        LOG.info("message: " + message);
        LOG.info("session: " + session);
        LOG.info("- - - - - - - - - - - - - - - - - - - - - - - -");

        if (message instanceof MapMessage mapMessage) {
            try {
                Long counterpartyId = mapMessage.getLong("counterpartyId");
                Long bankStatementDetailId = mapMessage.getLong("bankStatementDetailId");

                if (counterpartyId > 0) {
                    LOG.info("updating counterparty for record: " + bankStatementDetailId + " with value: " + counterpartyId);
                    int cnt = bankStatementDetailService.setCounterpartyId(counterpartyId, bankStatementDetailId);
                    LOG.info(cnt + " record[s] updated");
                    LOG.info("- - - - - - - - - - - - - - - - - - - - - - - -");
                }
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
}