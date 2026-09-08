package com.transportadora.erp.dto;

import com.transportadora.erp.model.Rota;
import com.transportadora.erp.model.StatusRota;
import java.time.LocalDateTime;

public record RotaResponseDTO(
    Long id,
    String codigo,
    String origem,
    String destino,
    StatusRota status,
    LocalDateTime dataCriacao
) {
    // Construtor utilitário para converter Model -> DTO
    public RotaResponseDTO(Rota rota) {
        this(rota.getId(), rota.getCodigo(), rota.getOrigem(), rota.getDestino(), rota.getStatus(), rota.getDataCriacao());
    }
}