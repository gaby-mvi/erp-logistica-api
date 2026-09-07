package com.transportadora.erp.repository;

import com.transportadora.erp.model.Marketplace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketplaceRepository extends JpaRepository<Marketplace, Long> {

    boolean existsByCnpj(String cnpj);

    Optional<Marketplace> findByCnpj(String cnpj);
}