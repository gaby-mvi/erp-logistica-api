package com.transportadora.erp.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import com.transportadora.erp.model.*;
import com.transportadora.erp.repository.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springdoc.core.annotations.ParameterObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/rotas")
@RequiredArgsConstructor
public class RotaController {

    private final RotaRepository rotaRepository;
    private final MotoristaRepository motoristaRepository;
    private final VeiculoRepository veiculoRepository;
    private final PacoteRepository pacoteRepository;

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Rota rota) {
        if (rotaRepository.existsByCodigoRomaneio(rota.getCodigoRomaneio())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe um romaneio cadastrado com este código.");
        }

        if (rota.getMotorista() == null || rota.getMotorista().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O ID do motorista é obrigatório.");
        }
        Motorista motorista = motoristaRepository.findById(rota.getMotorista().getId()).orElse(null);
        if (motorista == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Motorista não encontrado.");
        }

        if (rota.getVeiculo() == null || rota.getVeiculo().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O ID do veículo é obrigatório.");
        }
        Veiculo veiculo = veiculoRepository.findById(rota.getVeiculo().getId()).orElse(null);
        if (veiculo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veículo não encontrado.");
        }

        List<Pacote> pacotesEncontrados = new ArrayList<>();
        if (rota.getPacotes() != null && !rota.getPacotes().isEmpty()) {
            for (Pacote p : rota.getPacotes()) {
                if (p.getId() != null) {
                    pacoteRepository.findById(p.getId()).ifPresent(pacotesEncontrados::add);
                }
            }
        }

        rota.setMotorista(motorista);
        rota.setVeiculo(veiculo);
        rota.setPacotes(pacotesEncontrados);

        if (rota.getStatus() == null) {
            rota.setStatus(StatusRota.PLANEJADA);
        }

        Rota salva = rotaRepository.save(rota);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<?> iniciarRota(@PathVariable Long id) {
        return rotaRepository.findById(id).map(rota -> {
            rota.setStatus(StatusRota.EM_TRANSITO);
            rota.setDataInicio(OffsetDateTime.now());

            if (rota.getPacotes() != null) {
                for (Pacote pacote : rota.getPacotes()) {
                    pacote.setStatus(StatusPacote.EM_TRANSITO);
                    pacoteRepository.save(pacote);
                }
            }

            Rota atualizada = rotaRepository.save(rota);
            return ResponseEntity.ok(atualizada);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Rota>> listar(
            @RequestParam(required = false) StatusRota status,
            @RequestParam(required = false) Long motoristaId,
            @RequestParam(required = false) OffsetDateTime dataInicio,
            @RequestParam(required = false) OffsetDateTime dataFim,
            @ParameterObject @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        if (dataInicio != null && dataFim != null) {
            return ResponseEntity.ok(rotaRepository.findByDataInicioBetween(dataInicio, dataFim, pageable));
        }

        if (status != null && motoristaId != null) {
            return ResponseEntity.ok(rotaRepository.findByStatusAndMotoristaId(status, motoristaId, pageable));
        }

        if (status != null) {
            return ResponseEntity.ok(rotaRepository.findByStatus(status, pageable));
        }

        if (motoristaId != null) {
            return ResponseEntity.ok(rotaRepository.findByMotoristaId(motoristaId, pageable));
        }

        return ResponseEntity.ok(rotaRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rota> buscarPorId(@PathVariable Long id) {
        return rotaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizarRota(@PathVariable Long id) {
        return rotaRepository.findById(id).map(rota -> {
            rota.setStatus(StatusRota.FINALIZADA);
            rota.setDataFim(OffsetDateTime.now());

            // Garante que os pacotes em trânsito da rota sejam marcados como ENTREGUE
            if (rota.getPacotes() != null) {
                for (Pacote pacote : rota.getPacotes()) {
                    if (pacote.getStatus() == StatusPacote.EM_TRANSITO) {
                        pacote.setStatus(StatusPacote.ENTREGUE);
                        pacoteRepository.save(pacote);
                    }
                }
            }

            Rota atualizada = rotaRepository.save(rota);
            return ResponseEntity.ok(atualizada);
        }).orElse(ResponseEntity.notFound().build());
    }
}