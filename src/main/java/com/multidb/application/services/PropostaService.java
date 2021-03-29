package com.multidb.application.services;

import com.multidb.adapters.persistence.sybase.entities.PropostaEntity;
import com.multidb.adapters.persistence.sybase.repositories.PropostaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PropostaService {

    private PropostaRepository repository;

    public List<PropostaEntity> getConsistenciasAberto(LocalDateTime dataInicio, LocalDateTime dataFim){
        return repository.extraiConsistenciasAberto(dataInicio, dataFim);
    }

}
