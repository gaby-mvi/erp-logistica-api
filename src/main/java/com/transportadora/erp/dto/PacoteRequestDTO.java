package com.transportadora.erp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PacoteRequestDTO(
    @NotBlank(message = "O código de rastreio é obrigatório")
    String codigoRastreio,

    @NotBlank(message = "O nome do destinatário é obrigatório")
    String destinatarioNome,

    @NotBlank(message = "O endereço de entrega é obrigatório")
    String destinatarioEndereco,

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d{3}|\\d{8}", message = "O CEP deve ser válido (ex: 12345-678 ou 12345678)")
    String destinatarioCep,

    @NotNull(message = "O ID do Marketplace é obrigatório")
    Long marketplaceId
) {}