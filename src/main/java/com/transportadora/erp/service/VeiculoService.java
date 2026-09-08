package com.transportadora.erp.service;

import com.transportadora.erp.model.Veiculo;
import com.transportadora.erp.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    @Transactional(readOnly = true)
    public List<Veiculo> listarTodos() {
        return veiculoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Veiculo buscarPorId(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com o ID: " + id));
    }

    @Transactional
    public Veiculo criar(Veiculo veiculo) {
        if (veiculoRepository.existsByPlaca(veiculo.getPlaca())) {
            throw new IllegalArgumentException("Já existe um veículo cadastrado com a placa informada.");
        }
        return veiculoRepository.save(veiculo);
    }

    @Transactional
    public Veiculo atualizar(Long id, Veiculo dados) {
        Veiculo existente = buscarPorId(id);

        if (!existente.getPlaca().equalsIgnoreCase(dados.getPlaca()) &&
                veiculoRepository.existsByPlaca(dados.getPlaca())) {
            throw new IllegalArgumentException("Já existe outro veículo cadastrado com a placa informada.");
        }

        existente.setPlaca(dados.getPlaca());
        existente.setModelo(dados.getModelo());
        existente.setMarca(dados.getMarca());
        existente.setTipo(dados.getTipo());

        if (dados.getAtivo() != null) {
            existente.setAtivo(dados.getAtivo());
        }

        return veiculoRepository.save(existente);
    }

    @Transactional
    public void deletar(Long id) {
        Veiculo existente = buscarPorId(id);
        veiculoRepository.delete(existente);
    }
}