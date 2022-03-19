package com.indrachrsm.ewallet.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class KafkaCallback implements ListenableFutureCallback<SendResult<String, String>> {

    private Logger logger = LoggerFactory.getLogger(KafkaCallback.class);

    private String dataToSend;

    public KafkaCallback(String dataToSend) {
        this.dataToSend = dataToSend;
    }

    @Override
    public void onFailure(Throwable ex) {
        logger.error("Failed send to kafka, data: {}.", dataToSend);
        logger.error(ex.getMessage());
        throw new KafkaException(ex.getMessage());
    }

    @Override
    public void onSuccess(SendResult<String, String> result) {
        logger.info("Succeed send to kafka, data: {}.", dataToSend);
    }
}

