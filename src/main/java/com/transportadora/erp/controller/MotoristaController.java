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

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Motorista motoristaAtualizado) {
        return motoristaRepository.findById(id)
                .map(existente -> {
                    // Valida se o CPF foi alterado e se já pertence a outro motorista
                    if (!existente.getCpf().equals(motoristaAtualizado.getCpf()) &&
                        motoristaRepository.existsByCpf(motoristaAtualizado.getCpf())) {
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body("Já existe outro motorista cadastrado com o CPF informado.");
                    }

                    // Valida se a CNH foi alterada e se já pertence a outro motorista
                    if (!existente.getCnh().equals(motoristaAtualizado.getCnh()) &&
                        motoristaRepository.existsByCnh(motoristaAtualizado.getCnh())) {
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body("Já existe outro motorista cadastrado com a CNH informada.");
                    }

                    existente.setNome(motoristaAtualizado.getNome());
                    existente.setCpf(motoristaAtualizado.getCpf());
                    existente.setCnh(motoristaAtualizado.getCnh());
                    existente.setTelefone(motoristaAtualizado.getTelefone());

                    if (motoristaAtualizado.getAtivo() != null) {
                        existente.setAtivo(motoristaAtualizado.getAtivo());
                    }

                    Motorista salvo = motoristaRepository.save(existente);
                    return ResponseEntity.ok(salvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!motoristaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        motoristaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}