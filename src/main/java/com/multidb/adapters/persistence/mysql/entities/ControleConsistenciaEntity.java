package com.multidb.adapters.persistence.mysql.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ControleConsistencia")
public class ControleConsistenciaEntity {

    @Id
    private String codigoContrato;

    @Column(nullable = true)
    private Boolean indicadorPagamentoComissao;

    private LocalDateTime dataEnvioConsulta;

    @Column(nullable = true)
    private LocalDateTime dataRetornoConsulta;
}
