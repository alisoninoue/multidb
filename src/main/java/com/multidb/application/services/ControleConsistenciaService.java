package com.multidb.application.services;

import com.multidb.adapters.persistence.mysql.entities.ControleConsistenciaEntity;
import com.multidb.adapters.persistence.mysql.repositories.ControleConsistenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ControleConsistenciaService {

    private final ControleConsistenciaRepository repository;

    public List<ControleConsistenciaEntity> saveAll(List<ControleConsistenciaEntity> consistencias) {
        return (List<ControleConsistenciaEntity>) repository.saveAll(consistencias);
    }
}
