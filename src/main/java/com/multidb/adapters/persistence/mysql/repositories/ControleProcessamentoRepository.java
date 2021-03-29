package com.multidb.adapters.persistence.mysql.repositories;

import com.multidb.adapters.persistence.mysql.entities.ControleProcessamentoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControleProcessamentoRepository extends CrudRepository<ControleProcessamentoEntity, String> {

}
