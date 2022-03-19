package com.indrachrsm.ewallet.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class KafkaProducerService {

    private Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendData(String dataToSend, String topic) {
        logger.info("Send to Kafka, topic:: [{}] :: START.", topic);

        if (dataToSend == null) {
            logger.error("Failed send to kafka, data was empty/null.");
            throw new KafkaException("Failed send to kafka, data was empty/null.");
        }

        try {
            ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(topic, dataToSend);
            future.addCallback(new KafkaCallback(dataToSend));
        } catch (Exception exception) {
            logger.error("Error msg : {}", exception.getMessage());
        } finally {
            this.kafkaTemplate.flush();
        }

        logger.info("Send to Kafka, topic:: [{}] :: END.", topic);
    }
}

