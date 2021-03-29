package com.multidb.application.services;

import com.multidb.adapters.persistence.mysql.entities.ControleProcessamentoEntity;
import com.multidb.adapters.persistence.mysql.repositories.ControleProcessamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ControleProcessamento {

    private ControleProcessamentoRepository repository;

    public ControleProcessamentoEntity getDataInicioProcessamento(String rotina){

        Optional<ControleProcessamentoEntity> controle = repository.findById(rotina);
        if (controle.isPresent()) {
            return controle.get();

        } else {
            LocalDateTime dataInicioPrimeiraExecucao = LocalDate.now().atTime(0, 0, 0);
            ControleProcessamentoEntity controleProcessamentoEntity = new ControleProcessamentoEntity();
            controleProcessamentoEntity.setCodigoRotina(rotina);
            controleProcessamentoEntity.setDataUltimaExecucao(dataInicioPrimeiraExecucao);
            return controleProcessamentoEntity;
        }
    }

    public ControleProcessamentoEntity updateDataUltimaExecucao(ControleProcessamentoEntity entity) {
        return repository.save(entity);
    }
}
