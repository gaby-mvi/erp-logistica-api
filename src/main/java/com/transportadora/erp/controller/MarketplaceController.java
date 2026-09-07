package com.transportadora.erp.controller;

import com.transportadora.erp.model.Marketplace;
import com.transportadora.erp.repository.MarketplaceRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marketplaces")
@RequiredArgsConstructor
public class MarketplaceController {

    private final MarketplaceRepository marketplaceRepository;

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Marketplace marketplace) {
        if (marketplaceRepository.existsByCnpj(marketplace.getCnpj())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe um marketplace cadastrado com o CNPJ informado.");
        }

        Marketplace salvo = marketplaceRepository.save(marketplace);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Marketplace>> listarTodos() {
        return ResponseEntity.ok(marketplaceRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marketplace> buscarPorId(@PathVariable Long id) {
        return marketplaceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Marketplace marketplaceAtualizado) {
        return marketplaceRepository.findById(id)
                .map(existente -> {
                    // Valida se o CNPJ mudou e se o novo já pertence a outro registro
                    if (!existente.getCnpj().equals(marketplaceAtualizado.getCnpj()) &&
                            marketplaceRepository.existsByCnpj(marketplaceAtualizado.getCnpj())) {
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body("Já existe outro marketplace cadastrado com o CNPJ informado.");
                    }

                    existente.setNome(marketplaceAtualizado.getNome());
                    existente.setCnpj(marketplaceAtualizado.getCnpj());
                    existente.setEmail(marketplaceAtualizado.getEmail());

                    if (marketplaceAtualizado.getStatus() != null) {
                        existente.setStatus(marketplaceAtualizado.getStatus());
                    }

                    Marketplace salvo = marketplaceRepository.save(existente);
                    return ResponseEntity.ok(salvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!marketplaceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        marketplaceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}