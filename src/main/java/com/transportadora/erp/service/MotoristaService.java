package com.transportadora.erp.service;

import com.transportadora.erp.dto.MotoristaRequestDTO;
import com.transportadora.erp.dto.MotoristaResponseDTO;
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
    public List<MotoristaResponseDTO> listarTodos() {
        return motoristaRepository.findAll()
                .stream()
                .map(MotoristaResponseDTO::new)
                .toList();
    }

    @Transactional
    public MotoristaResponseDTO cadastrar(MotoristaRequestDTO dto) {
        // Criando a entidade via Builder com os dados validados do DTO
        Motorista motorista = Motorista.builder()
                .nome(dto.nome())
                .cpf(dto.cpf())
                .cnh(dto.cnh())
                .telefone(dto.telefone())
                .ativo(true)
                .build();

        Motorista salvo = motoristaRepository.save(motorista);
        return new MotoristaResponseDTO(salvo);
    }
}