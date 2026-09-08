// src/main/java/com/transportadora/erp/dto/ErroValidacaoDTO.java
package com.transportadora.erp.dto;

import org.springframework.validation.FieldError;

public record ErroValidacaoDTO(
    String campo,
    String mensagem
) {
    public ErroValidacaoDTO(FieldError erro) {
        this(erro.getField(), erro.getDefaultMessage());
    }
}