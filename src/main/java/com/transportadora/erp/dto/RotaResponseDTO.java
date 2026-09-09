package com.transportadora.erp.dto;

import com.transportadora.erp.model.Rota;
import com.transportadora.erp.model.StatusRota;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record RotaResponseDTO(
    Long id,
    String codigo,
    String origem,
    String destino,
    StatusRota status,
    BigDecimal valorRepasse,
    Long motoristaId,
    String motoristaNome,
    Long veiculoId,
    String veiculoPlaca,
    Long marketplaceId,
    String marketplaceNome,
    OffsetDateTime criadoEm
) {
    public RotaResponseDTO(Rota rota) {
        this(
            rota.getId(),
            rota.getCodigo(),
            rota.getOrigem(),
            rota.getDestino(),
            rota.getStatus(),
            rota.getValorRepasse() != null ? rota.getValorRepasse() : BigDecimal.ZERO,
            rota.getMotorista() != null ? rota.getMotorista().getId() : null,
            rota.getMotorista() != null ? rota.getMotorista().getNome() : null,
            rota.getVeiculo() != null ? rota.getVeiculo().getId() : null,
            rota.getVeiculo() != null ? rota.getVeiculo().getPlaca() : null,
            rota.getMarketplace() != null ? rota.getMarketplace().getId() : null,
            rota.getMarketplace() != null ? rota.getMarketplace().getNome() : null,
            rota.getCriadoEm()
        );
    }
}