package com.transportadora.erp.dto;

import com.transportadora.erp.model.TipoVeiculo;
import com.transportadora.erp.model.Veiculo;

public record VeiculoResponseDTO(
    Long id,
    String placa,
    String modelo,
    String marca,
    TipoVeiculo tipo,
    Boolean ativo
) {
    public VeiculoResponseDTO(Veiculo v) {
        this(
            v.getId(), // Herdado da EntidadeBase
            v.getPlaca(),
            v.getModelo(),
            v.getMarca(),
            v.getTipo(),
            v.getAtivo()
        );
    }
}