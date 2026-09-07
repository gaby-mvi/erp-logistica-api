package com.transportadora.erp.dto;

import com.transportadora.erp.model.Perfil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroUsuarioDto(
    @NotBlank String nome,
    @NotBlank String email,
    @NotBlank String senha,
    @NotNull Perfil perfil
) {}