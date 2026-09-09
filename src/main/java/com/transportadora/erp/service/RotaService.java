package com.transportadora.erp.service;

import com.transportadora.erp.dto.RotaRequestDTO;
import com.transportadora.erp.dto.RotaResponseDTO;
import com.transportadora.erp.model.Marketplace;
import com.transportadora.erp.model.Motorista;
import com.transportadora.erp.model.Rota;
import com.transportadora.erp.model.StatusRota;
import com.transportadora.erp.model.Veiculo;
import com.transportadora.erp.repository.MarketplaceRepository;
import com.transportadora.erp.repository.MotoristaRepository;
import com.transportadora.erp.repository.RotaRepository;
import com.transportadora.erp.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RotaService {

    private final RotaRepository rotaRepository;
    private final MotoristaRepository motoristaRepository;
    private final VeiculoRepository veiculoRepository;
    private final MarketplaceRepository marketplaceRepository;

    @Transactional(readOnly = true)
    public List<RotaResponseDTO> listarTodas() {
        return rotaRepository.findAll()
                .stream()
                .map(RotaResponseDTO::new)
                .toList();
    }

    @Transactional
    public RotaResponseDTO criarRota(RotaRequestDTO dto) {
        Motorista motorista = motoristaRepository.findById(dto.motoristaId())
                .orElseThrow(() -> new EntityNotFoundException("Motorista não encontrado com o ID: " + dto.motoristaId()));

        Veiculo veiculo = veiculoRepository.findById(dto.veiculoId())
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado com o ID: " + dto.veiculoId()));

        Marketplace marketplace = null;
        if (dto.marketplaceId() != null) {
            marketplace = marketplaceRepository.findById(dto.marketplaceId()).orElse(null);
        }

        // Gera o código automaticamente: ROT-AAAAMMDD-XXXX (ex: ROT-20260908-A1B2)
        String sufixo = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        String codigoAuto = "ROT-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + sufixo;

        // Origem padrão se não for informada
        String origemPadrao = (dto.origem() != null && !dto.origem().isBlank()) ? dto.origem() : "Galpão SP-01";

        Rota rota = Rota.builder()
                .codigo(codigoAuto)
                .origem(origemPadrao)
                .destino(dto.destino())
                .motorista(motorista)
                .veiculo(veiculo)
                .marketplace(marketplace)
                .valorRepasse(dto.valorRepasse())
                .status(StatusRota.EM_ANDAMENTO)
                .build();

        Rota rotaSalva = rotaRepository.save(rota);
        return new RotaResponseDTO(rotaSalva);
    }
}