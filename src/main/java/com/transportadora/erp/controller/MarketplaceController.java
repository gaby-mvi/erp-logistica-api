package com.transportadora.erp.controller;

import com.transportadora.erp.dto.MarketplaceRequestDTO;
import com.transportadora.erp.dto.MarketplaceResponseDTO;
import com.transportadora.erp.service.MarketplaceService;
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

    private final MarketplaceService marketplaceService;

    @GetMapping
    public ResponseEntity<List<MarketplaceResponseDTO>> listar() {
        return ResponseEntity.ok(marketplaceService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<MarketplaceResponseDTO> cadastrar(@RequestBody @Valid MarketplaceRequestDTO dto) {
        MarketplaceResponseDTO novo = marketplaceService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }
}