package com.transportadora.erp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record RotaRequestDTO(
    @NotNull(message = "O ID do motorista é obrigatório")
    Long motoristaId,

    @NotNull(message = "O ID do veículo é obrigatório")
    Long veiculoId,

    Long marketplaceId,

    @NotBlank(message = "O destino é obrigatório")
    String destino,

    @NotNull(message = "O valor do repasse é obrigatório")
    @Positive(message = "O valor do repasse deve ser maior que zero")
    BigDecimal valorRepasse,

    String origem // Opcional: se o front não enviar, assume o Galpão padrão
) {}