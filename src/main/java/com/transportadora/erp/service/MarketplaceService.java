package com.transportadora.erp.service;

import com.transportadora.erp.dto.MarketplaceRequestDTO;
import com.transportadora.erp.dto.MarketplaceResponseDTO;
import com.transportadora.erp.model.Marketplace;
import com.transportadora.erp.repository.MarketplaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketplaceService {

    private final MarketplaceRepository marketplaceRepository;

    @Transactional(readOnly = true)
    public List<MarketplaceResponseDTO> listarTodos() {
        return marketplaceRepository.findAll()
                .stream()
                .map(MarketplaceResponseDTO::new)
                .toList();
    }

    @Transactional
    public MarketplaceResponseDTO cadastrar(MarketplaceRequestDTO dto) {
        Marketplace marketplace = Marketplace.builder()
                .nome(dto.nome())
                .cnpj(dto.cnpj())
                .email(dto.email())
                .status("ATIVO")
                .build();

        Marketplace salvo = marketplaceRepository.save(marketplace);
        return new MarketplaceResponseDTO(salvo);
    }
}