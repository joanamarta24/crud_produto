package com.senai.crud_produto.dto;

import com.senai.crud_produto.entity.Alimentos;
import com.senai.crud_produto.entity.Limpeza;
import com.senai.crud_produto.entity.Produto;

import java.math.BigDecimal;

public class ProdutoDTO {

    public enum TipoProduto {
        LIMPEZA,
        ALIMENTO
    }

    public record ProdutoRequest(
            String id,
            String nome,
            String marca,
            BigDecimal preco,
            Integer quantidade,
            Boolean perecivel,
            String perfume,
            TipoProduto tipo
    ) {
        public Produto toEntity() {
            return switch (this.tipo) {
                case LIMPEZA -> Limpeza.builder()
                        .nome(this.nome)
                        .preco(this.preco)
                        .marca(this.marca)
                        .quantidade(this.quantidade)
                        .perfume(this.perfume)
                        .build();

                case ALIMENTO -> Alimentos.builder()
                        .nome(this.nome)
                        .preco(this.preco)
                        .marca(this.marca)
                        .quantidade(this.quantidade)
                        .perecivel(this.perecivel)
                        .build();

                default -> throw new RuntimeException("Tipo de produto inválido");
            };
        }
    }

    public record ProdutoResponse(
            String id,
            String nome,
            String marca,
            BigDecimal preco,
            Integer quantidade,
            Boolean perecivel,
            String perfume,
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
}
