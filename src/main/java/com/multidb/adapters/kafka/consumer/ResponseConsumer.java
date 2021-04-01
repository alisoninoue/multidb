package com.multidb.adapters.kafka.consumer;

import com.multidb.application.services.ControleConsistenciaService;
import com.multidb.avro.ConsultaRealizada;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResponseConsumer {

    private final ControleConsistenciaService service;

    @KafkaListener(
            topics = "${topic.name.consumer}",
            groupId = "${topic.group-id.consulta-realizada}",
            clientIdPrefix = "topic.client-id.consulta-realizada")
    public void listenGroupFoo(@Payload ConsumerRecord<String, ConsultaRealizada> consumer,
                               Acknowledgment ack) {
        log.info("Received Message in group foo: " + consumer.value());
        log.info("Headers: {}", consumer.headers());



        consumer.headers().forEach(h -> log.info("Header key: {} value: {}", h.key(), new String(h.value(), StandardCharsets.UTF_8)));
        ack.acknowledge();
    }
}
