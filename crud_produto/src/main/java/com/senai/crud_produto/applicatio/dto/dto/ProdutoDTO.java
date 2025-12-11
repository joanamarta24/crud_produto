package com.senai.crud_produto.applicatio.dto.dto;

import com.senai.crud_produto.domain.entity.entity.Alimentos;
import com.senai.crud_produto.domain.entity.entity.Limpeza;
import com.senai.crud_produto.domain.entity.entity.Produto;

import java.math.BigDecimal;

public class ProdutoDTO {

    public interface ProdutoRequest {
        Produto toEntity();
    }

    public record ProdutoBaseRequest(
            String nome,
            String marca,
            BigDecimal preco,
            Integer quantidade
    ) {}

    public record LimpezaRequest(
            ProdutoBaseRequest base,
            String perfume
    ) implements ProdutoRequest {
        public Produto toEntity() {
            return Limpeza.builder()
                    .nome(base.nome())
                    .marca(base.marca())
                    .preco(base.preco())
                    .quantidade(base.quantidade())
                    .perfume(Boolean.valueOf(this.perfume))
                    .build();
        }
    }

    public record AlimentoRequest(
            ProdutoBaseRequest base,
            Boolean perecivel
    ) implements ProdutoRequest {
        public Produto toEntity() {
            return Alimentos.builder()
                    .nome(base.nome())
                    .marca(base.marca())
                    .preco(base.preco())
                    .quantidade(base.quantidade())
                    .perecivel(this.perecivel)
                    .build();
        }
    }
}