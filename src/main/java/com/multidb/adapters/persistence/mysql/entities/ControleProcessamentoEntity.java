package com.multidb.adapters.persistence.mysql.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ControleProcessamento")
public class ControleProcessamentoEntity {

    @Id
    private String codigoRotina;

    private LocalDateTime dataUltimaExecucao;
}
