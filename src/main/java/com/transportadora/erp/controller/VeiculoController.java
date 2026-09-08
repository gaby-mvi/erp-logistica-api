package com.transportadora.erp.controller;

import com.transportadora.erp.dto.VeiculoRequestDTO;
import com.transportadora.erp.dto.VeiculoResponseDTO;
import com.transportadora.erp.service.VeiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final VeiculoService veiculoService;

    @GetMapping
    public ResponseEntity<List<VeiculoResponseDTO>> listar() {
        return ResponseEntity.ok(veiculoService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<VeiculoResponseDTO> cadastrar(@RequestBody @Valid VeiculoRequestDTO dto) {
        VeiculoResponseDTO novo = veiculoService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }
}