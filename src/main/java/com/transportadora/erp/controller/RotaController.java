package com.transportadora.erp.controller;

import com.transportadora.erp.dto.RotaRequestDTO;
import com.transportadora.erp.dto.RotaResponseDTO;
import com.transportadora.erp.service.RotaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rotas")
@RequiredArgsConstructor
public class RotaController {

    private final RotaService rotaService;

    @GetMapping
    public ResponseEntity<List<RotaResponseDTO>> listar() {
        List<RotaResponseDTO> rotas = rotaService.listarTodas();
        return ResponseEntity.ok(rotas);
    }

    @PostMapping
    public ResponseEntity<RotaResponseDTO> criar(@RequestBody @Valid RotaRequestDTO dto) {
        RotaResponseDTO novaRota = rotaService.criarRota(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaRota);
    }
}