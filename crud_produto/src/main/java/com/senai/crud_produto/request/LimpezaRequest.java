package com.senai.crud_produto.request;

import com.senai.crud_produto.domain.entity.entity.Limpeza;
import com.senai.crud_produto.dto.Enum.TipoProduto;

import java.math.BigDecimal;

public record LimpezaRequest(
        String nome,
        String marca,
        BigDecimal preco,
        Integer quantidade,
        String perfume
) {
    // Validações
    public LimpezaRequest {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("Marca é obrigatória");
        }
        if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        if (quantidade == null || quantidade < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }
        if (perfume == null || perfume.trim().isEmpty()) {
            throw new IllegalArgumentException("Perfume é obrigatório");
        }
    }

    // Converte para entidade
    public Limpeza toEntity() {
        return Limpeza.builder()
                .nome(this.nome)
                .marca(this.marca)
                .preco(this.preco)
                .quantidade(this.quantidade)
                .perfume(Boolean.valueOf(this.perfume))
                .build();
    }

    // Converte para ProdutoRequest genérico (se necessário)
    public ProdutoRequest toProdutoRequest() {
        return new ProdutoRequest(
                this.nome,
                this.marca,
                this.preco,
                this.quantidade,
                null, // perecivel
                this.perfume,
                TipoProduto.LIMPEZA
        );
    }

    // Métodos with para imutabilidade
    public LimpezaRequest withNome(String novoNome) {
        return new LimpezaRequest(novoNome, marca, preco, quantidade, perfume);
    }

    public LimpezaRequest withMarca(String novaMarca) {
        return new LimpezaRequest(nome, novaMarca, preco, quantidade, perfume);
    }

    public LimpezaRequest withPreco(BigDecimal novoPreco) {
        return new LimpezaRequest(nome, marca, novoPreco, quantidade, perfume);
    }

    public LimpezaRequest withQuantidade(Integer novaQuantidade) {
        return new LimpezaRequest(nome, marca, preco, novaQuantidade, perfume);
    }

    public LimpezaRequest withPerfume(String novoPerfume) {
        return new LimpezaRequest(nome, marca, preco, quantidade, novoPerfume);
    }

}

