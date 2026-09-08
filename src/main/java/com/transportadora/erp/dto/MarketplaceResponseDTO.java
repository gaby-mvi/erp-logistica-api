package com.transportadora.erp.dto;

import com.transportadora.erp.model.Marketplace;

public record MarketplaceResponseDTO(
    Long id,
    String nome,
    String cnpj,
    String email,
    String status
) {
    public MarketplaceResponseDTO(Marketplace m) {
        this(
            m.getId(), // Herdado da EntidadeBase
            m.getNome(),
            m.getCnpj(),
            m.getEmail(),
            m.getStatus()
        );
    }
}