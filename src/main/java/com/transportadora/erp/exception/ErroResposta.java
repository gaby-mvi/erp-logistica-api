package com.transportadora.erp.exception;

import java.time.OffsetDateTime;
import java.util.List;

public record ErroResposta(
    int status,
    String mensagem,
    OffsetDateTime timestamp,
    List<String> detalhes
) {}