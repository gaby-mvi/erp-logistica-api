package com.transportadora.erp.service;

import com.transportadora.erp.dto.DashboardResumoResponseDTO;
import com.transportadora.erp.model.StatusPacote;
import com.transportadora.erp.model.StatusRota;
import com.transportadora.erp.repository.PacoteRepository;
import com.transportadora.erp.repository.RotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final PacoteRepository pacoteRepository;
    private final RotaRepository rotaRepository;

    @Transactional(readOnly = true)
    public DashboardResumoResponseDTO obterResumoOperacional() {
        // Métricas de Pacotes
        long pacotesAguardandoColeta = pacoteRepository.countByStatus(StatusPacote.AGUARDANDO_COLETA);
        long pacotesRecebidoNoGalpao = pacoteRepository.countByStatus(StatusPacote.RECEBIDO_NO_GALPAO);
        long pacotesEmTransito = pacoteRepository.countByStatus(StatusPacote.EM_TRANSITO);
        long pacotesEntregues = pacoteRepository.countByStatus(StatusPacote.ENTREGUE);
        long pacotesFalhaEntrega = pacoteRepository.countByStatus(StatusPacote.FALHA_ENTREGA);

        // Métricas de Rotas
        long rotasEmAndamento = rotaRepository.countByStatus(StatusRota.EM_ANDAMENTO);
        long rotasConcluidas = rotaRepository.countByStatus(StatusRota.CONCLUIDA);
        long rotasCanceladas = rotaRepository.countByStatus(StatusRota.CANCELADA);

        return new DashboardResumoResponseDTO(
            pacotesAguardandoColeta,
            pacotesRecebidoNoGalpao,
            pacotesEmTransito,
            pacotesEntregues,
            pacotesFalhaEntrega,
            rotasEmAndamento,
            rotasConcluidas,
            rotasCanceladas
        );
    }
}