package com.multidb.adapters.persistence.mysql.repositories;

import com.multidb.adapters.persistence.mysql.entities.TipoRotinaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRotinaRepository extends CrudRepository<TipoRotinaEntity, String> {
}
