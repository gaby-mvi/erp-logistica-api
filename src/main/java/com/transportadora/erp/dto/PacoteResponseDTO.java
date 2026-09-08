package com.transportadora.erp.dto;

import com.transportadora.erp.model.Pacote;
import com.transportadora.erp.model.StatusPacote;

public record PacoteResponseDTO(
    Long id,
    String codigoRastreio,
    String destinatarioNome,
    String destinatarioEndereco,
    String destinatarioCep,
    StatusPacote status,
    Long marketplaceId,
    String marketplaceNome
) {
    public PacoteResponseDTO(Pacote p) {
        this(
            p.getId(), // Herdado da EntidadeBase
            p.getCodigoRastreio(),
            p.getDestinatarioNome(),
            p.getDestinatarioEndereco(),
            p.getDestinatarioCep(),
            p.getStatus(),
            p.getMarketplace() != null ? p.getMarketplace().getId() : null,
            p.getMarketplace() != null ? p.getMarketplace().getNome() : null
        );
    }
}