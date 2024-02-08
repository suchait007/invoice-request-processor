package com.invoice.request.processor.function.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;


@Configuration
@RequiredArgsConstructor
public class JmsConfig {

    @Value("${spring.jms.servicebus.connection-string}")
    private String connectionString;

    /*
    @Bean
    public ServiceBusJmsConnectionFactory jmsConnectionFactory() {
        ServiceBusJmsConnectionFactory serviceBusJmsConnectionFactory =  new ServiceBusJmsConnectionFactory(connectionString, null);
        return serviceBusJmsConnectionFactory;
    }

     */
    @Bean("customObjectMapper")
    public ObjectMapper getMapper() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                          DefaultJmsListenerContainerFactoryConfigurer configurer) {

        DefaultJmsListenerContainerFactory listenerContainerFactory = new DefaultJmsListenerContainerFactory();
        configurer.configure(listenerContainerFactory, connectionFactory);
        listenerContainerFactory.setTransactionManager(null);
        listenerContainerFactory.setSessionTransacted(false);

        return listenerContainerFactory;

    }

    @Bean
    public JmsTemplate jmsTemplate(final ConnectionFactory connectionFactory, final MappingJackson2MessageConverter messageConverter) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setMessageConverter(messageConverter);
        return jmsTemplate;
    }

    @Bean
    public MappingJackson2MessageConverter jacksonJmsMessageConverter(@Qualifier("customObjectMapper") ObjectMapper mapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(mapper);
        return converter;
    }


}
