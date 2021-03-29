package com.multidb.adapters.persistence.sybase.repositories;

import com.multidb.adapters.persistence.sybase.entities.PropostaEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PropostaRepository extends CrudRepository<PropostaEntity, String> {

    @Query(value = "select numeroContrato," +
            "codigoConvenio," +
            "numeroMatricula," +
            "dataInclusao " +
            "from PropostaEntity " +
            "where dataInclusao >= :dataInicio " +
            "  and dataInclusao < :dataFim", nativeQuery = true)
    List<PropostaEntity> extraiConsistenciasAberto(@Param("dataInicio") LocalDateTime dataInicio,
                                                   @Param("dataFim") LocalDateTime dataFim);
}
