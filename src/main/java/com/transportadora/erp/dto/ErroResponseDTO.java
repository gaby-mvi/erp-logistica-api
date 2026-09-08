// src/main/java/com/transportadora/erp/dto/ErroResponseDTO.java
package com.transportadora.erp.dto;

import java.time.LocalDateTime;

public record ErroResponseDTO(
    int status,
    String mensagem,
    LocalDateTime timestamp
) {
    public ErroResponseDTO(int status, String mensagem) {
        this(status, mensagem, LocalDateTime.now());
    }
}