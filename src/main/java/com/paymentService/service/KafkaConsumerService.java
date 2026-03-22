package com.paymentService.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    // Listens to events, for example from Customer Service or a Payment Gateway
    @KafkaListener(topics = "payment-events", groupId = "payment-group")
    public void consumePaymentEvent(String message) {
        logger.info("Received message from Kafka topic 'payment-events': {}", message);
        
        // TODO: Process the event. 
        // If Customer Service is sending a request to check payment status, process it here.
    }

    @KafkaListener(topics = "booking-created", groupId = "payment-group")
    public void consumeBookingCreated(String message) {
        logger.info("Received booking event from 'booking-created': {}", message);
        
        // TODO: Create a pending payment record or notify external gateway
    }
}
