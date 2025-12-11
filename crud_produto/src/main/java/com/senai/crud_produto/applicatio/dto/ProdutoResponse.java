package com.senai.crud_produto.applicatio.dto;

import com.senai.crud_produto.domain.entity.entity.Limpeza;

import java.math.BigDecimal;

// Interface comum para todos os DTOs de produto
public interface ProdutoResponse {
    String id();
    String nome();
    String marca();
    BigDecimal preco();
    Integer quantidade();
    ProdutoTipo tipo();

    enum ProdutoTipo {
        LIMPEZA, ALIMENTO
    }
}

// Implementação para Limpeza
public record LimpezaResponseDTO(
        String id,
        String nome,
        String marca,
        BigDecimal preco,
        Integer quantidade,
        String perfume
) implements ProdutoResponse {

    @Override
    public ProdutoResponse.ProdutoTipo tipo() {
        return ProdutoResponse.ProdutoTipo.LIMPEZA;
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