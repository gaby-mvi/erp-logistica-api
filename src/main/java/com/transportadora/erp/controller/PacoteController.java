package com.transportadora.erp.controller;

import com.transportadora.erp.dto.PacoteRequestDTO;
import com.transportadora.erp.dto.PacoteResponseDTO;
import com.transportadora.erp.model.StatusPacote;
import com.transportadora.erp.service.PacoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pacotes")
@RequiredArgsConstructor
public class PacoteController {

    private final PacoteService pacoteService;

    @GetMapping
    public ResponseEntity<Page<PacoteResponseDTO>> listar(
            @RequestParam(required = false) StatusPacote status,
            @RequestParam(required = false) Long marketplaceId,
            @PageableDefault(size = 10, sort = "criadoEm") Pageable pageable) {
        return ResponseEntity.ok(pacoteService.listarComFiltros(status, marketplaceId, pageable));
    }

    @GetMapping("/rastreio/{codigo}")
    public ResponseEntity<PacoteResponseDTO> buscarPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(pacoteService.buscarPorCodigoRastreio(codigo));
    }

    @PostMapping
    public ResponseEntity<PacoteResponseDTO> cadastrar(@RequestBody @Valid PacoteRequestDTO dto) {
        PacoteResponseDTO novo = pacoteService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PacoteResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusPacote status) {
        return ResponseEntity.ok(pacoteService.atualizarStatus(id, status));
    }
}