package com.transportadora.erp.controller;

import com.transportadora.erp.model.Motorista;
import com.transportadora.erp.repository.MotoristaRepository;
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

    private final MotoristaRepository motoristaRepository;

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Motorista motorista) {
        if (motoristaRepository.existsByCpf(motorista.getCpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe um motorista cadastrado com o CPF informado.");
        }
        if (motoristaRepository.existsByCnh(motorista.getCnh())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe um motorista cadastrado com a CNH informada.");
        }

        Motorista salvo = motoristaRepository.save(motorista);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Motorista>> listarTodos() {
        return ResponseEntity.ok(motoristaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Motorista> buscarPorId(@PathVariable Long id) {
        return motoristaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}