package com.transportadora.erp.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "rota")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // <--- ESSA ANOTAÇÃO É OBRIGATÓRIA PARA USAR .builder()
public class Rota extends EntidadeBase {

    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(nullable = false, length = 150)
    private String origem;

    @Column(nullable = false, length = 150)
    private String destino;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatusRota status = StatusRota.EM_ANDAMENTO;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorRepasse;

    @ManyToOne
    @JoinColumn(name = "motorista_id", nullable = false)
    private Motorista motorista;

    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "marketplace_id")
    private Marketplace marketplace;
}