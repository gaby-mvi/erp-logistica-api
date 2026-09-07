package com.transportadora.erp.repository;

import com.transportadora.erp.model.Rota;
import com.transportadora.erp.model.StatusRota;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface RotaRepository extends JpaRepository<Rota, Long> {

    boolean existsByCodigoRomaneio(String codigoRomaneio);

    Optional<Rota> findByCodigoRomaneio(String codigoRomaneio);

    Page<Rota> findByStatus(StatusRota status, Pageable pageable);

    Page<Rota> findByMotoristaId(Long motoristaId, Pageable pageable);

    Page<Rota> findByStatusAndMotoristaId(StatusRota status, Long motoristaId, Pageable pageable);

    Page<Rota> findByDataInicioBetween(OffsetDateTime inicio, OffsetDateTime fim, Pageable pageable);

    long countByStatus(StatusRota status);
}