package com.transportadora.erp.repository;

import com.transportadora.erp.model.Rota;
import com.transportadora.erp.model.StatusRota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RotaRepository extends JpaRepository<Rota, Long> {

    long countByStatus(StatusRota status);
}