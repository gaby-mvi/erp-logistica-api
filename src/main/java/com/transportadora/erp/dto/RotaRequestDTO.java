package com.transportadora.erp.dto;

import jakarta.validation.constraints.NotBlank;

public record RotaRequestDTO(
    @NotBlank(message = "O código da rota é obrigatório")
    String codigo,
    
    @NotBlank(message = "A origem é obrigatória")
    String origem,
    
    @NotBlank(message = "O destino é obrigatório")
    String destino
) {}