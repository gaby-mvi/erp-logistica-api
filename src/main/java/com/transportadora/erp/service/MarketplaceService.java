package com.transportadora.erp.service;

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
    public List<Marketplace> listarTodos() {
        return marketplaceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Marketplace buscarPorId(Long id) {
        return marketplaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marketplace não encontrado com o ID: " + id));
    }

    @Transactional
    public Marketplace criar(Marketplace marketplace) {
        if (marketplaceRepository.existsByCnpj(marketplace.getCnpj())) {
            throw new IllegalArgumentException("Já existe um marketplace cadastrado com o CNPJ informado.");
        }
        return marketplaceRepository.save(marketplace);
    }

    @Transactional
    public Marketplace atualizar(Long id, Marketplace dados) {
        Marketplace existente = buscarPorId(id);

        if (!existente.getCnpj().equals(dados.getCnpj()) &&
                marketplaceRepository.existsByCnpj(dados.getCnpj())) {
            throw new IllegalArgumentException("Já existe outro marketplace cadastrado com o CNPJ informado.");
        }

        existente.setNome(dados.getNome());
        existente.setCnpj(dados.getCnpj());
        existente.setEmail(dados.getEmail());

        if (dados.getStatus() != null) {
            existente.setStatus(dados.getStatus());
        }

        return marketplaceRepository.save(existente);
    }

    @Transactional
    public void deletar(Long id) {
        Marketplace existente = buscarPorId(id);
        marketplaceRepository.delete(existente);
    }
}