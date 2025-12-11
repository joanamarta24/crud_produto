package com.senai.crud_produto.applicatio.dto;

import com.senai.crud_produto.domain.entity.entity.Alimentos;

import java.math.BigDecimal;

public record AlimentoDTO(
        String id,
        String nome,
        String marca,
        BigDecimal preco,
        Integer quantidade,
        Boolean perecivel
) {
    public static AlimentoDTO fromEntity(Alimentos alimento) {
        return new AlimentoDTO(
                alimento.getId().toString(),
                alimento.getNome(),
                alimento.getMarca(),
                alimento.getPreco(),
                alimento.getQuantidade(),
                alimento.getPerecivel()
        );
    }

}

