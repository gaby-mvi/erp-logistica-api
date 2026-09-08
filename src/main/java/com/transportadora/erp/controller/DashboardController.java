package com.transportadora.erp.controller;

import com.transportadora.erp.dto.DashboardResumoResponseDTO;
import com.transportadora.erp.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/resumo")
    public ResponseEntity<DashboardResumoResponseDTO> obterResumoOperacional() {
        return ResponseEntity.ok(dashboardService.obterResumoOperacional());
    }
}