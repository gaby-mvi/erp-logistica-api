package com.transportadora.erp.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import com.transportadora.erp.model.Marketplace;
import com.transportadora.erp.model.Ocorrencia;
import com.transportadora.erp.model.Pacote;
import com.transportadora.erp.model.StatusPacote;
import com.transportadora.erp.model.TipoOcorrencia;
import com.transportadora.erp.repository.MarketplaceRepository;
import com.transportadora.erp.repository.OcorrenciaRepository;
import com.transportadora.erp.repository.PacoteRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;

@RestController
@RequestMapping("/api/pacotes")
@RequiredArgsConstructor
public class PacoteController {

    private final PacoteRepository pacoteRepository;
    private final MarketplaceRepository marketplaceRepository;
    private final OcorrenciaRepository ocorrenciaRepository;

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Pacote pacote) {
        if (pacoteRepository.existsByCodigoRastreio(pacote.getCodigoRastreio())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe um pacote cadastrado com este código de rastreio.");
        }

        if (pacote.getMarketplace() == null || pacote.getMarketplace().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("O ID do Marketplace é obrigatório.");
        }

        Marketplace marketplace = marketplaceRepository.findById(pacote.getMarketplace().getId())
                .orElse(null);

        if (marketplace == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Marketplace não encontrado com o ID informado.");
        }

        pacote.setMarketplace(marketplace);

        // Garante que o status tenha valor caso não venha no JSON
        if (pacote.getStatus() == null) {
            pacote.setStatus(StatusPacote.AGUARDANDO_COLETA);
        }

        Pacote salvo = pacoteRepository.save(pacote);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public ResponseEntity<Page<Pacote>> listar(
            @RequestParam(required = false) StatusPacote status,
            @RequestParam(required = false) Long marketplaceId,
            @ParameterObject @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        if (status != null && marketplaceId != null) {
            return ResponseEntity.ok(pacoteRepository.findByStatusAndMarketplaceId(status, marketplaceId, pageable));
        }
        if (status != null) {
            return ResponseEntity.ok(pacoteRepository.findByStatus(status, pageable));
        }
        if (marketplaceId != null) {
            return ResponseEntity.ok(pacoteRepository.findByMarketplaceId(marketplaceId, pageable));
        }

        return ResponseEntity.ok(pacoteRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pacote> buscarPorId(@PathVariable Long id) {
        return pacoteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rastreio/{codigoRastreio}")
    public ResponseEntity<Pacote> buscarPorRastreio(@PathVariable String codigoRastreio) {
        return pacoteRepository.findByCodigoRastreio(codigoRastreio)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusPacote status) {

        return pacoteRepository.findById(id).map(pacote -> {
            pacote.setStatus(status);
            Pacote atualizado = pacoteRepository.save(pacote);
            return ResponseEntity.ok(atualizado);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/ocorrencias")
    public ResponseEntity<?> registrarOcorrencia(
            @PathVariable Long id,
            @Valid @RequestBody Ocorrencia ocorrencia) {

        return pacoteRepository.findById(id).map(pacote -> {
            ocorrencia.setPacote(pacote);

            // Atualiza o status do pacote automaticamente com base no tipo da ocorrência
            if (ocorrencia.getTipo() == TipoOcorrencia.ENTREGA_REALIZADA) {
                pacote.setStatus(StatusPacote.ENTREGUE);
            } else {
                pacote.setStatus(StatusPacote.FALHA_ENTREGA);
            }

            pacoteRepository.save(pacote);
            Ocorrencia salva = ocorrenciaRepository.save(ocorrencia);

            return ResponseEntity.status(HttpStatus.CREATED).body(salva);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/ocorrencias")
    public ResponseEntity<List<Ocorrencia>> listarOcorrenciasPorPacote(@PathVariable Long id) {
        if (!pacoteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ocorrenciaRepository.findByPacoteIdOrderByDataHoraDesc(id));
    }
}