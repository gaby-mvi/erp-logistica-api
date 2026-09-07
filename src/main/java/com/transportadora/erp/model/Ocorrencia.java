package com.transportadora.erp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "ocorrencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ocorrencia extends EntidadeBase {

    @ManyToOne
    @JoinColumn(name = "pacote_id", nullable = false)
    private Pacote pacote;

    @NotNull(message = "O tipo da ocorrência é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoOcorrencia tipo;

    @Column(length = 500)
    private String descricao;

    private OffsetDateTime dataHora;

    @PrePersist
    public void prePersist() {
        if (this.dataHora == null) {
            this.dataHora = OffsetDateTime.now();
        }
    }
}