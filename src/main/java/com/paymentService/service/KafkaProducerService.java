package com.paymentService.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // Use this method to send payment status updates to Customer Service
    public void sendPaymentStatus(String topic, String message) {
        logger.info("Sending message to Kafka topic '{}': {}", topic, message);
        kafkaTemplate.send(topic, message);
    }
}
