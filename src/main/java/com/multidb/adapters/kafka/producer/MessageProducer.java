package com.multidb.adapters.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageProducer {

    private final KafkaTemplate<String, ProducerRecord<String, SpecificRecord>> kafkaTemplate;

    public void sendMessage(String topicName, SpecificRecord message) {
        ProducerRecord producerRecord = new ProducerRecord<String, SpecificRecord>(topicName, message);
        producerRecord.headers().add("correlation-id", UUID.randomUUID().toString().getBytes());
        ListenableFuture<SendResult<String, ProducerRecord<String, SpecificRecord>>> future =
                kafkaTemplate.send(producerRecord);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, ProducerRecord<String, SpecificRecord>> result) {
                log.info("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("Unable to send message=["
                        + message + "] due to : " + ex.getMessage());
            }
        });
    }
}
