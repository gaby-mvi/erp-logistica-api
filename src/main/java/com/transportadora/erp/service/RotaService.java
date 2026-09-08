package com.transportadora.erp.service;

import com.transportadora.erp.dto.RotaRequestDTO;
import com.transportadora.erp.dto.RotaResponseDTO;
import com.transportadora.erp.model.Rota;
import com.transportadora.erp.repository.RotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RotaService {

    private final RotaRepository rotaRepository;

    @Transactional(readOnly = true)
    public List<RotaResponseDTO> listarTodas() {
        return rotaRepository.findAll()
                .stream()
                .map(RotaResponseDTO::new)
                .toList();
    }

    @Transactional
    public RotaResponseDTO criarRota(RotaRequestDTO dto) {
        Rota rota = new Rota();
        rota.setCodigo(dto.codigo());
        rota.setOrigem(dto.origem());
        rota.setDestino(dto.destino());

        Rota rotaSalva = rotaRepository.save(rota);
        return new RotaResponseDTO(rotaSalva);
    }
}