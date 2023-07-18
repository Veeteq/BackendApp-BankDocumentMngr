package com.veeteq.finance.bankdocument.jms;

import static com.veeteq.finance.bankdocument.jms.JmsConfiguration.COUNTERPARTY_REQUEST_QUEUE;
import static com.veeteq.finance.bankdocument.jms.JmsConfiguration.COUNTERPARTY_RESPONSE_QUEUE;

import java.util.List;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.veeteq.finance.bankdocument.dto.BankDataDTO;

@Service
public class MessageQueueService {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public MessageQueueService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void registerBankStatement(List<BankDataDTO> bankData) {
        System.out.println("the total amount of details is: " + bankData.size());

        ObjectMapper mapper = new ObjectMapper();

        bankData.stream()
                .limit(3)
                .forEach(detail -> {
                    System.out.println("registering bank statement detail: " + detail.getTitle());

                    jmsTemplate.setReceiveTimeout(1000L);

                    jmsTemplate.send(COUNTERPARTY_REQUEST_QUEUE, new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) {
                            try {
                                TextMessage message = session.createTextMessage(mapper.writeValueAsString(detail));
                                message.setJMSCorrelationID(UUID.randomUUID().toString());
                                message.setJMSReplyTo(new ActiveMQQueue(COUNTERPARTY_RESPONSE_QUEUE));
                                return message;
                            } catch (JMSException exc) {
                                throw new RuntimeException(exc.getMessage(), exc);
                            } catch (JsonProcessingException exc) {
                                throw new RuntimeException(exc.getMessage(), exc);
                            }
                        }
                    });
                });
    }

}
