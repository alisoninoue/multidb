package com.multidb.adapters.persistence.mysql.repositories;

import com.multidb.adapters.persistence.mysql.entities.ControleConsistenciaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControleConsistenciaRepository extends CrudRepository<ControleConsistenciaEntity, String> {
}
