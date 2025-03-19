package com.bridglelabz.addressbookapp.rabbitmq;

import com.bridglelabz.addressbookapp.dto.EmailDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressBookProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    String EXCHANGE = "email.exchange";
    String ROUTING_KEY = "email.routingKey";

    public void sendMessage(String message) {
        amqpTemplate.convertAndSend("addressbook.exchange", "addressbook.routingKey", message);
        System.out.println("Message sent to RabbitMQ: " + message);
    }
    public void sendEmailMessage(String email, String subject, String body) {
        EmailDTO emailDTO = new EmailDTO(email, subject, body);
        rabbitTemplate.convertAndSend("addressbook.exchange", "email.routingKey", emailDTO);
    }
}
