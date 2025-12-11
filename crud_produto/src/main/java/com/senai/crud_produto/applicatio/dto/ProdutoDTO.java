package com.senai.crud_produto.applicatio.dto;

import com.senai.crud_produto.domain.entity.entity.Alimentos;
import com.senai.crud_produto.domain.entity.entity.Limpeza;
import com.senai.crud_produto.domain.entity.entity.Produto;

import java.math.BigDecimal;

public class ProdutoDTO {

    public enum TipoProduto {
        LIMPEZA,
        ALIMENTO
    }

    public record ProdutoResponse(
            Long id,
            String nome,
            String marca,
            BigDecimal preco,
            Integer quantidade,
            Boolean perecivel,
            Boolean perfume,
            TipoProduto tipo
    ) {
        public static ProdutoResponse fromEntity(Produto produto) {
            if (produto instanceof Alimentos alimentos) {
                return new ProdutoResponse(
                        alimentos.getId().toString(),
                        alimentos.getNome(),
                        alimentos.getMarca(),
                        alimentos.getPreco(),
                        alimentos.getQuantidade(),
                        alimentos.getPerecivel(),
                        null,
                        TipoProduto.ALIMENTO
                );
            } else if (produto instanceof Limpeza limpeza) {
                return new ProdutoResponse(
                        limpeza.getId().toString(),
                        limpeza.getNome(),
                        limpeza.getMarca(),
                        limpeza.getPreco(),
                        limpeza.getQuantidade(),
                        null,
                        limpeza.getPerfume(),
                        TipoProduto.LIMPEZA
                );
            } else {
                throw new RuntimeException("Tipo de produto inválido");
            }
        }
    }

    public record ProdutoRequest() {
    }
}