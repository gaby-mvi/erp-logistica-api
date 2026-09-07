package com.transportadora.erp.repository;

import com.transportadora.erp.model.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MotoristaRepository extends JpaRepository<Motorista, Long> {

    boolean existsByCpf(String cpf);

    boolean existsByCnh(String cnh);

    Optional<Motorista> findByCpf(String cpf);
}