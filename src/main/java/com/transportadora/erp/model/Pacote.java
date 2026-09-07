package com.transportadora.erp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pacote")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pacote extends EntidadeBase {

    @NotBlank(message = "O código de rastreio é obrigatório")
    @Column(nullable = false, unique = true, length = 50)
    private String codigoRastreio;

    @NotBlank(message = "O nome do destinatário é obrigatório")
    @Column(nullable = false, length = 100)
    private String destinatarioNome;

    @NotBlank(message = "O endereço de entrega é obrigatório")
    @Column(nullable = false, length = 200)
    private String destinatarioEndereco;

    @NotBlank(message = "O CEP é obrigatório")
    @Column(nullable = false, length = 9)
    private String destinatarioCep;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatusPacote status = StatusPacote.AGUARDANDO_COLETA;

    @ManyToOne
    @JoinColumn(name = "marketplace_id", nullable = false)
    private Marketplace marketplace;

    @PrePersist
    public void prePersistPacote() {
        if (this.status == null) {
            this.status = StatusPacote.AGUARDANDO_COLETA;
        }
    }
}