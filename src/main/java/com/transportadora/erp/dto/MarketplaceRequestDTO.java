package com.transportadora.erp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MarketplaceRequestDTO(
    @NotBlank(message = "O nome do marketplace é obrigatório")
    String nome,

    @NotBlank(message = "O CNPJ é obrigatório")
    @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter exatamente 14 dígitos numéricos")
    String cnpj,

    @Email(message = "E-mail inválido")
    String email
) {}