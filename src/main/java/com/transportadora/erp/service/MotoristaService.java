package com.transportadora.erp.service;

import com.transportadora.erp.model.Motorista;
import com.transportadora.erp.repository.MotoristaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MotoristaService {

    private final MotoristaRepository motoristaRepository;

    @Transactional(readOnly = true)
    public List<Motorista> listarTodos() {
        return motoristaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Motorista buscarPorId(Long id) {
        return motoristaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Motorista não encontrado com o ID: " + id));
    }

    @Transactional
    public Motorista criar(Motorista motorista) {
        if (motoristaRepository.existsByCpf(motorista.getCpf())) {
            throw new IllegalArgumentException("Já existe um motorista cadastrado com o CPF informado.");
        }
        if (motoristaRepository.existsByCnh(motorista.getCnh())) {
            throw new IllegalArgumentException("Já existe um motorista cadastrado com a CNH informada.");
        }
        return motoristaRepository.save(motorista);
    }

    @Transactional
    public Motorista atualizar(Long id, Motorista dados) {
        Motorista existente = buscarPorId(id);

        if (!existente.getCpf().equals(dados.getCpf()) &&
                motoristaRepository.existsByCpf(dados.getCpf())) {
            throw new IllegalArgumentException("Já existe outro motorista cadastrado com o CPF informado.");
        }

        if (!existente.getCnh().equals(dados.getCnh()) &&
                motoristaRepository.existsByCnh(dados.getCnh())) {
            throw new IllegalArgumentException("Já existe outro motorista cadastrado com a CNH informada.");
        }

        existente.setNome(dados.getNome());
        existente.setCpf(dados.getCpf());
        existente.setCnh(dados.getCnh());
        existente.setTelefone(dados.getTelefone());

        if (dados.getAtivo() != null) {
            existente.setAtivo(dados.getAtivo());
        }

        return motoristaRepository.save(existente);
    }

    @Transactional
    public void deletar(Long id) {
        Motorista existente = buscarPorId(id);
        motoristaRepository.delete(existente);
    }
}