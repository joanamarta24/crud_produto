package com.senai.crud_produto.applicatio.dto;

import com.senai.crud_produto.domain.entity.entity.Limpeza;

import java.math.BigDecimal;

// Implementação para Limpeza
public record LimpezaResponseDTO(
        String id,
        String nome,
        String marca,
        BigDecimal preco,
        Integer quantidade,
        Boolean perfume
) implements ProdutoResponse {

    @Override
    public ProdutoTipo tipo() {
        return ProdutoTipo.LIMPEZA;
    }

    public static LimpezaResponseDTO fromEntity(Limpeza limpeza) {
        return new LimpezaResponseDTO(
                limpeza.getId().toString(),
                limpeza.getNome(),
                limpeza.getMarca(),
                limpeza.getPreco(),
                limpeza.getQuantidade(),
                limpeza.getPerfume()
        );
    }
}
