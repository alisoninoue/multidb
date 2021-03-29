package com.multidb.adapters.persistence.sybase.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropostaEntity {

    @Id
    private String numeroContrato;

    private String codigoConvenio;

    private String numeroMatricula;

    private LocalDateTime dataInclusao;
}
