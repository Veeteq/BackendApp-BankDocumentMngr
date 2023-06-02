package com.veeteq.finance.bankdocument.jms;

import javax.jms.Session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

/**
 * https://memorynotfound.com/spring-boot-embedded-activemq-configuration-example/
 */


@Configuration
@EnableJms
public class JmsConfiguration {
    public static final String COUNTERPARTY_QUEUE = "cprt-queue";

    @Bean
    public JmsListenerContainerFactory<?> queueListenerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        factory.setErrorHandler(t -> System.out.println("An exception occured " + t.getMessage()));
        factory.setMessageConverter(messageConverter());

        return factory;
    }

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.BYTES);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
 
}
