package com.transportadora.erp.dto;

import com.transportadora.erp.model.Motorista;

public record MotoristaResponseDTO(
    Long id,
    String nome,
    String cpf,
    String cnh,
    String telefone,
    Boolean ativo
) {
    public MotoristaResponseDTO(Motorista m) {
        this(
            m.getId(), // Herdado da EntidadeBase
            m.getNome(),
            m.getCpf(),
            m.getCnh(),
            m.getTelefone(),
            m.getAtivo()
        );
    }
}