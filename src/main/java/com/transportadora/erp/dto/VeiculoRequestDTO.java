package com.transportadora.erp.dto;

import com.transportadora.erp.model.TipoVeiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VeiculoRequestDTO(
    @NotBlank(message = "A placa é obrigatória")
    String placa,

    @NotBlank(message = "O modelo é obrigatório")
    String modelo,

    @NotBlank(message = "A marca é obrigatória")
    String marca,

    @NotNull(message = "O tipo do veículo é obrigatório")
    TipoVeiculo tipo
) {}