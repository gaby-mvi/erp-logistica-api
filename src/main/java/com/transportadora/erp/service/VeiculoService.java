package com.transportadora.erp.service;

import com.transportadora.erp.dto.VeiculoRequestDTO;
import com.transportadora.erp.dto.VeiculoResponseDTO;
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
    public List<VeiculoResponseDTO> listarTodos() {
        return veiculoRepository.findAll()
                .stream()
                .map(VeiculoResponseDTO::new)
                .toList();
    }

    @Transactional
    public VeiculoResponseDTO cadastrar(VeiculoRequestDTO dto) {
        Veiculo veiculo = Veiculo.builder()
                .placa(dto.placa())
                .modelo(dto.modelo())
                .marca(dto.marca())
                .tipo(dto.tipo())
                .ativo(true)
                .build();

        Veiculo salvo = veiculoRepository.save(veiculo);
        return new VeiculoResponseDTO(salvo);
    }
}