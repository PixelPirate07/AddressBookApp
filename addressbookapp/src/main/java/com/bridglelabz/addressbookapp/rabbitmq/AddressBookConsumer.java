package com.bridglelabz.addressbookapp.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AddressBookConsumer {

    @RabbitListener(queues = "addressbook.queue")
    public void receiveMessage(String message) {
        System.out.println("Received message from RabbitMQ: " + message);
    }
}
