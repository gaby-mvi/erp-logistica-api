package com.transportadora.erp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rotas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Rota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private String origem;
    private String destino;
    
    @Enumerated(EnumType.STRING)
    private StatusRota status; // Ex: EM_ANDAMENTO, CONCLUIDA, CANCELADA

    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        if (this.status == null) {
            this.status = StatusRota.EM_ANDAMENTO;
        }
    }
}