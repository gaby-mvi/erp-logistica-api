package com.transportadora.erp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rota")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rota extends EntidadeBase {

    @NotBlank(message = "O código do romaneio é obrigatório")
    @Column(nullable = false, unique = true, length = 50)
    private String codigoRomaneio;

    @NotNull(message = "O motorista é obrigatório")
    @ManyToOne
    @JoinColumn(name = "motorista_id", nullable = false)
    private Motorista motorista;

    @NotNull(message = "O veículo é obrigatório")
    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatusRota status = StatusRota.PLANEJADA;

    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;

    @OneToMany
    @JoinColumn(name = "rota_id")
    @Builder.Default
    private List<Pacote> pacotes = new ArrayList<>();

    @PrePersist
    public void prePersistRota() {
        if (this.status == null) {
            this.status = StatusRota.PLANEJADA;
        }
    }
}