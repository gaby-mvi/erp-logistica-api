package com.transportadora.erp.dto;

public record TokenDto(
    String token,
    String tipo,
    String email,
    String perfil
) {}