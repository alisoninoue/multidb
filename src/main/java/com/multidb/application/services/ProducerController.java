package com.multidb.application.services;

import com.multidb.adapters.kafka.producer.MessageProducer;
import com.multidb.application.dto.ConsultaRealizadaDto;
import com.multidb.avro.ConsultaRealizada;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProducerController {

    private final MessageProducer messageProducer;

    @Value("${topic.name.producer.consulta-realizada}")
    private String topicName;

    @PostMapping("/consultaRealizada")
    public void produceMessageKafka(@RequestBody ConsultaRealizadaDto consultaRealizadaDto){
        ConsultaRealizada consultaRealizadaAvro = consultaRealizadaDto.toAvro();
        messageProducer.sendMessage(topicName, consultaRealizadaAvro);
    }

}
