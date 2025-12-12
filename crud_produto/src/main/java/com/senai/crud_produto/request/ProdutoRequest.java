package com.senai.crud_produto.request;

import com.senai.crud_produto.domain.entity.entity.Alimentos;
import com.senai.crud_produto.domain.entity.entity.Limpeza;
import com.senai.crud_produto.domain.entity.entity.Produto;
import com.senai.crud_produto.dto.Enum.TipoProduto;
import java.math.BigDecimal;


public record ProdutoRequest(
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
                    .marca(this.marca)
                    .preco(this.preco)
                    .quantidade(this.quantidade)
                    .perfume(Boolean.valueOf(this.perfume))
                    .build();

            case ALIMENTO -> Alimentos.builder()
                    .nome(this.nome)
                    .marca(this.marca)
                    .preco(this.preco)
                    .quantidade(this.quantidade)
                    .perecivel(this.perecivel)
                    .build();

            default -> throw new RuntimeException("Tipo de produto inválido");
        };
    }
    public ProdutoRequest withNome(String novoNome) {
        return new ProdutoRequest(
                novoNome, marca, preco, quantidade, perecivel, perfume, tipo
        );
    }

    public ProdutoRequest withMarca(String novaMarca) {
        return new ProdutoRequest(
                nome, novaMarca, preco, quantidade, perecivel, perfume, tipo
        );
    }

    public ProdutoRequest withPreco(BigDecimal novoPreco) {
        return new ProdutoRequest(
                nome, marca, novoPreco, quantidade, perecivel, perfume, tipo
        );
    }
}

