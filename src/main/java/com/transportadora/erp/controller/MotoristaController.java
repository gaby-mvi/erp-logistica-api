package com.transportadora.erp.controller;

import com.transportadora.erp.dto.MotoristaRequestDTO;
import com.transportadora.erp.dto.MotoristaResponseDTO;
import com.transportadora.erp.service.MotoristaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/motoristas")
@RequiredArgsConstructor
public class MotoristaController {

    private final MotoristaService motoristaService;

    @GetMapping
    public ResponseEntity<List<MotoristaResponseDTO>> listar() {
        return ResponseEntity.ok(motoristaService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<MotoristaResponseDTO> cadastrar(@RequestBody @Valid MotoristaRequestDTO dto) {
        MotoristaResponseDTO novoMotorista = motoristaService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMotorista);
    }
}