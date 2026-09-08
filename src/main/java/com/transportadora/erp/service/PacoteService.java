package com.transportadora.erp.service;

import com.transportadora.erp.dto.PacoteRequestDTO;
import com.transportadora.erp.dto.PacoteResponseDTO;
import com.transportadora.erp.model.Marketplace;
import com.transportadora.erp.model.Pacote;
import com.transportadora.erp.model.StatusPacote;
import com.transportadora.erp.repository.MarketplaceRepository;
import com.transportadora.erp.repository.PacoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PacoteService {

    private final PacoteRepository pacoteRepository;
    private final MarketplaceRepository marketplaceRepository;

    @Transactional(readOnly = true)
    public Page<PacoteResponseDTO> listarComFiltros(StatusPacote status, Long marketplaceId, Pageable pageable) {
        if (status != null && marketplaceId != null) {
            return pacoteRepository.findByStatusAndMarketplaceId(status, marketplaceId, pageable)
                    .map(PacoteResponseDTO::new);
        }
        if (status != null) {
            return pacoteRepository.findByStatus(status, pageable)
                    .map(PacoteResponseDTO::new);
        }
        if (marketplaceId != null) {
            return pacoteRepository.findByMarketplaceId(marketplaceId, pageable)
                    .map(PacoteResponseDTO::new);
        }
        return pacoteRepository.findAll(pageable)
                .map(PacoteResponseDTO::new);
    }

    @Transactional(readOnly = true)
    public PacoteResponseDTO buscarPorCodigoRastreio(String codigoRastreio) {
        Pacote pacote = pacoteRepository.findByCodigoRastreio(codigoRastreio)
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado com o código: " + codigoRastreio));
        return new PacoteResponseDTO(pacote);
    }

    @Transactional
    public PacoteResponseDTO cadastrar(PacoteRequestDTO dto) {
        if (pacoteRepository.existsByCodigoRastreio(dto.codigoRastreio())) {
            throw new IllegalArgumentException("Já existe um pacote cadastrado com o código de rastreio informado.");
        }

        Marketplace marketplace = marketplaceRepository.findById(dto.marketplaceId())
                .orElseThrow(() -> new RuntimeException("Marketplace não encontrado com o ID: " + dto.marketplaceId()));

        Pacote pacote = Pacote.builder()
                .codigoRastreio(dto.codigoRastreio())
                .destinatarioNome(dto.destinatarioNome())
                .destinatarioEndereco(dto.destinatarioEndereco())
                .destinatarioCep(dto.destinatarioCep())
                .status(StatusPacote.AGUARDANDO_COLETA)
                .marketplace(marketplace)
                .build();

        Pacote salvo = pacoteRepository.save(pacote);
        return new PacoteResponseDTO(salvo);
    }

    @Transactional
    public PacoteResponseDTO atualizarStatus(Long id, StatusPacote novoStatus) {
        Pacote pacote = pacoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado com o ID: " + id));

        pacote.setStatus(novoStatus);
        return new PacoteResponseDTO(pacoteRepository.save(pacote));
    }
}