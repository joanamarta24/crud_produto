package com.senai.crud_produto.applicatio.dto;

import com.senai.crud_produto.applicatio.dto.Enum.TipoProduto;
import com.senai.crud_produto.domain.entity.entity.Alimentos;
import com.senai.crud_produto.domain.entity.entity.Limpeza;
import com.senai.crud_produto.domain.entity.entity.Produto;

public class ProdutoResponse {

    // Interface comum para todos os tipos de produto
    public interface ProdutoResponseDTO {
        String id();
        String nome();
        String marca();
        java.math.BigDecimal preco();
        Integer quantidade();
        TipoProduto tipo();
    }

    // Factory para criar a resposta apropriada
    public static ProdutoResponseDTO fromEntity(Produto produto) {
        if (produto instanceof Alimentos alimento) {
            return new ProdutoResponseDTO() {
                @Override
                public String id() {
                    return alimento.getId().toString();
                }

                @Override
                public String nome() {
                    return alimento.getNome();
                }

                @Override
                public String marca() {
                    return alimento.getMarca();
                }

                @Override
                public java.math.BigDecimal preco() {
                    return alimento.getPreco();
                }

                @Override
                public Integer quantidade() {
                    return alimento.getQuantidade();
                }

                @Override
                public ProdutoDTO.TipoProduto tipo() {
                    return ProdutoDTO.TipoProduto.ALIMENTO;
                }

                // Método extra específico para alimentos
                public Boolean perecivel() {
                    return alimento.getPerecivel();
                }
            };
        } else if (produto instanceof Limpeza limpeza) {
            return new ProdutoResponseDTO() {
                @Override
                public String id() {
                    return limpeza.getId().toString();
                }

                @Override
                public String nome() {
                    return limpeza.getNome();
                }

                @Override
                public String marca() {
                    return limpeza.getMarca();
                }

                @Override
                public java.math.BigDecimal preco() {
                    return limpeza.getPreco();
                }

                @Override
                public Integer quantidade() {
                    return limpeza.getQuantidade();
                }

                @Override
                public ProdutoDTO.TipoProduto tipo() {
                    return ProdutoDTO.TipoProduto.LIMPEZA;
                }

                // Método extra específico para limpeza
                public String perfume() {
                    return limpeza.getPerfume();
                }
            };
        }
        throw new RuntimeException("Tipo de produto inválido");
    }
}