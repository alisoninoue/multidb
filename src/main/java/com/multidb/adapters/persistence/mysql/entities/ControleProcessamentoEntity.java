package com.multidb.adapters.persistence.mysql.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.CascadeType.ALL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ControleProcessamento")
public class ControleProcessamentoEntity {

    @Id
    @Column(name = "cod_rotina")
    private String codigoRotina;

    private LocalDateTime dataUltimaExecucao;

    @OneToOne(cascade = ALL)
    @PrimaryKeyJoinColumn
    private TipoRotinaEntity tipoRotinaEntity;
}
