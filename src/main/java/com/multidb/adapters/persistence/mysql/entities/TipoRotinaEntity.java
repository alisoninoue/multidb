package com.multidb.adapters.persistence.mysql.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoRotinaEntity {

    @Id
    @Column(name = "cod_rotina")
    private String codigoRotina;

    @Column(name = "nome_rotina")
    private String nomeRotina;
}
