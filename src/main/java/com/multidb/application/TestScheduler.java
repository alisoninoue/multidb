package com.multidb.application;

import com.multidb.adapters.kafka.producer.ComandoConsultaProducer;
import com.multidb.adapters.persistence.mysql.entities.ControleConsistenciaEntity;
import com.multidb.adapters.persistence.mysql.entities.ControleProcessamentoEntity;
import com.multidb.adapters.persistence.sybase.entities.PropostaEntity;
import com.multidb.application.services.ControleConsistenciaService;
import com.multidb.application.services.ControleProcessamento;
import com.multidb.application.services.PropostaService;
import com.multidb.avro.Consulta;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestScheduler {

    private final ControleConsistenciaService controleConsistenciaService;
    private final ControleProcessamento controleProcessamento;
    private final PropostaService propostaService;
    private final ComandoConsultaProducer comandoConsultaProducer;

    @Scheduled(cron = "0 0/1 * * * *")
    @SchedulerLock(name = "testLockMethod", lockAtMostFor = "2m", lockAtLeastFor = "30s")
    void execute() {
        log.info(LocalDateTime.now() + "Test Scheduler");

        ControleProcessamentoEntity controleProcessamentoEntity = controleProcessamento.getDataInicioProcessamento("rotina1");

        LocalDateTime dataInicio = controleProcessamentoEntity.getDataUltimaExecucao();
        LocalDateTime dataFim = LocalDateTime.now();


        log.info(controleProcessamentoEntity.toString());
        log.info("Data Inicio: {}", dataInicio);
        log.info("Data Fim: {}", dataFim);

        List<PropostaEntity> consistenciasAberto = propostaService.getConsistenciasAberto(dataInicio, dataFim);
        log.info("Extracao size: {}", consistenciasAberto.size());

        List<ControleConsistenciaEntity> controleConsistencias = new ArrayList<>();

        for (PropostaEntity consistencia : consistenciasAberto) {
            log.info("Consistencia {}", consistencia);
            Consulta consultaAvro = Consulta.newBuilder().setCodigoConvenio(consistencia.getCodigoConvenio())
                    .setDataConsulta(LocalDate.now().atTime(0, 0, 0).toString())
                    .setNumeroContrato(consistencia.getNumeroContrato())
                    .setNumeroMatricula(consistencia.getNumeroMatricula())
                    .build();
            comandoConsultaProducer.sendMessage(consultaAvro);

            ControleConsistenciaEntity controleConsistenciaEntity = new ControleConsistenciaEntity();
            controleConsistenciaEntity.setCodigoContrato(consistencia.getNumeroContrato());
            controleConsistenciaEntity.setDataEnvioConsulta(LocalDateTime.now());
            controleConsistencias.add(controleConsistenciaEntity);
        }

        controleConsistenciaService.saveAll(controleConsistencias);
        controleProcessamentoEntity.setDataUltimaExecucao(dataFim);
        controleProcessamento.updateDataUltimaExecucao(controleProcessamentoEntity);
    }
}
