package com.transportadora.erp.controller;

import com.transportadora.erp.model.StatusPacote;
import com.transportadora.erp.model.StatusRota;
import com.transportadora.erp.repository.PacoteRepository;
import com.transportadora.erp.repository.RotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final PacoteRepository pacoteRepository;
    private final RotaRepository rotaRepository;

    @GetMapping("/resumo")
    public ResponseEntity<Map<String, Object>> obterResumoOperacional() {
        Map<String, Object> resumo = new HashMap<>();

        // Métricas de Pacotes
        resumo.put("pacotesAguardandoColeta", pacoteRepository.countByStatus(StatusPacote.AGUARDANDO_COLETA));
        resumo.put("pacotesEmTransito", pacoteRepository.countByStatus(StatusPacote.EM_TRANSITO));
        resumo.put("pacotesEntregues", pacoteRepository.countByStatus(StatusPacote.ENTREGUE));
        resumo.put("pacotesFalhaEntrega", pacoteRepository.countByStatus(StatusPacote.FALHA_ENTREGA));

        // Métricas de Rotas
        resumo.put("rotasPlanejadas", rotaRepository.countByStatus(StatusRota.PLANEJADA));
        resumo.put("rotasEmTransito", rotaRepository.countByStatus(StatusRota.EM_TRANSITO));
        resumo.put("rotasFinalizadas", rotaRepository.countByStatus(StatusRota.FINALIZADA));

        return ResponseEntity.ok(resumo);
    }
}