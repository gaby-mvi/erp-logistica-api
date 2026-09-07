package com.transportadora.erp.repository;

import com.transportadora.erp.model.Pacote;
import com.transportadora.erp.model.StatusPacote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacoteRepository extends JpaRepository<Pacote, Long> {

    boolean existsByCodigoRastreio(String codigoRastreio);

    Optional<Pacote> findByCodigoRastreio(String codigoRastreio);

    Page<Pacote> findByMarketplaceId(Long marketplaceId, Pageable pageable);

    Page<Pacote> findByStatus(StatusPacote status, Pageable pageable);

    Page<Pacote> findByStatusAndMarketplaceId(StatusPacote status, Long marketplaceId, Pageable pageable);

    long countByStatus(StatusPacote status);
}