package com.veeteq.finance.bankdocument.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

/**
 * https://memorynotfound.com/spring-boot-embedded-activemq-configuration-example/
 * https://aniruthmp.github.io/Spring-JMS-request-response/
 */


@Configuration
@EnableJms
public class JmsConfiguration {
    public static final String COUNTERPARTY_REQUEST_QUEUE = "cprt-rqst-queue";
    public static final String COUNTERPARTY_RESPONSE_QUEUE = "cprt-resp-queue";

    @Bean
    public JmsListenerContainerFactory<?> queueListenerFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        factory.setErrorHandler(t -> System.out.println("######## An exception occured " + t.getMessage() + " #######"));
        //factory.setMessageConverter(messageConverter());

        configurer.configure(factory, connectionFactory);
        return factory;
    }


}
