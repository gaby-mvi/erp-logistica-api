package com.transportadora.erp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MotoristaRequestDTO(
    @NotBlank(message = "O nome é obrigatório")
    String nome,

    @NotBlank(message = "O CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter exatamente 11 dígitos")
    String cpf,

    @NotBlank(message = "A CNH é obrigatória")
    String cnh,

    String telefone
) {}