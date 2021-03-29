package com.multidb.application.dto;

import com.multidb.avro.ConsultaRealizada;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConsultaRealizadaDto {

    private String numeroContrato;

    private String codigoConvenio;

    private String numeroMatricula;

    private Boolean indicadorPagamento;

    public ConsultaRealizada toAvro() {
        return ConsultaRealizada.newBuilder()
                .setCodigoConvenio(this.codigoConvenio)
                .setIndicadorPagamento(this.indicadorPagamento)
                .setNumeroContrato(this.numeroContrato)
                .setNumeroMatricula(this.numeroMatricula)
                .build();
    }
}
