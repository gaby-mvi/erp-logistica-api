package com.transportadora.erp.controller;

import com.transportadora.erp.model.Veiculo;
import com.transportadora.erp.repository.VeiculoRepository;
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

    private final VeiculoRepository veiculoRepository;

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Veiculo veiculo) {
        if (veiculoRepository.existsByPlaca(veiculo.getPlaca())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe um veículo cadastrado com a placa informada.");
        }

        Veiculo salvo = veiculoRepository.save(veiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Veiculo>> listarTodos() {
        return ResponseEntity.ok(veiculoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> buscarPorId(@PathVariable Long id) {
        return veiculoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Veiculo veiculoAtualizado) {
        return veiculoRepository.findById(id)
                .map(existente -> {
                    // Valida se a placa foi alterada e se já pertence a outro veículo
                    if (!existente.getPlaca().equalsIgnoreCase(veiculoAtualizado.getPlaca()) &&
                        veiculoRepository.existsByPlaca(veiculoAtualizado.getPlaca())) {
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body("Já existe outro veículo cadastrado com a placa informada.");
                    }

                    existente.setPlaca(veiculoAtualizado.getPlaca());
                    existente.setModelo(veiculoAtualizado.getModelo());
                    existente.setMarca(veiculoAtualizado.getMarca());
                    existente.setTipo(veiculoAtualizado.getTipo());

                    if (veiculoAtualizado.getAtivo() != null) {
                        existente.setAtivo(veiculoAtualizado.getAtivo());
                    }

                    Veiculo salvo = veiculoRepository.save(existente);
                    return ResponseEntity.ok(salvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!veiculoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        veiculoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}