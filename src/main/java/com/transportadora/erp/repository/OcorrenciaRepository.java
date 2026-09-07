package com.transportadora.erp.repository;

import com.transportadora.erp.model.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {

    List<Ocorrencia> findByPacoteIdOrderByDataHoraDesc(Long pacoteId);
}