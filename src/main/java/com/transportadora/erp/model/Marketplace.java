package com.transportadora.erp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "marketplace")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Marketplace extends EntidadeBase {

    @NotBlank(message = "O nome do marketplace é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O CNPJ é obrigatório")
    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(length = 100)
    private String email;

    @Builder.Default
    @Column(length = 20)
    private String status = "ATIVO";
}