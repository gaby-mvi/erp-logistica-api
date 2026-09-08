package com.transportadora.erp.dto;

public record DashboardResumoResponseDTO(
    long pacotesAguardandoColeta,
    long pacotesRecebidoNoGalpao,
    long pacotesEmTransito,
    long pacotesEntregues,
    long pacotesFalhaEntrega,
    long rotasEmAndamento,
    long rotasConcluidas,
    long rotasCanceladas
) {}