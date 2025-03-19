package com.bridglelabz.addressbookapp.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Existing Queue for AddressBook
    @Bean
    public Queue addressBookQueue() {
        return new Queue("addressbook.queue", true);
    }

    // New Queue for Email Service
    @Bean
    public Queue emailQueue() {
        return new Queue("email.queue", true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("addressbook.exchange");
    }

    @Bean
    public Binding addressBookBinding(Queue addressBookQueue, TopicExchange exchange) {
        return BindingBuilder.bind(addressBookQueue)
                .to(exchange)
                .with("addressbook.routingKey");
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, TopicExchange exchange) {
        return BindingBuilder.bind(emailQueue)
                .to(exchange)
                .with("email.routingKey");
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
