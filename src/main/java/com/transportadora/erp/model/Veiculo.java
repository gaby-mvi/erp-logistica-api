package com.transportadora.erp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "veiculo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Veiculo extends EntidadeBase {

    @NotBlank(message = "A placa é obrigatória")
    @Column(nullable = false, unique = true, length = 8)
    private String placa;

    @NotBlank(message = "O modelo é obrigatório")
    @Column(nullable = false, length = 50)
    private String modelo;

    @NotBlank(message = "A marca é obrigatória")
    @Column(length = 50)
    private String marca;

    @NotNull(message = "O tipo do veículo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVeiculo tipo;

    @Builder.Default
    private Boolean ativo = true;
}