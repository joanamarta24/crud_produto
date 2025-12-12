package com.senai.crud_produto.dto;

import com.senai.crud_produto.domain.entity.entity.Alimentos;
import com.senai.crud_produto.dto.Enum.TipoProduto;
import com.senai.crud_produto.request.ProdutoRequest;

import java.math.BigDecimal;

// AlimentoRequest
public record AlimentoRequest(
        String nome,
        String marca,
        BigDecimal preco,
        Integer quantidade,
        Boolean perecivel
) {
    // Validações
    public AlimentoRequest {
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
        if (perecivel == null) {
            throw new IllegalArgumentException("Campo 'perecivel' é obrigatório");
        }
    }

    // Converte para entidade
    public Alimentos toEntity() {
        return Alimentos.builder()
                .nome(this.nome)
                .marca(this.marca)
                .preco(this.preco)
                .quantidade(this.quantidade)
                .perecivel(this.perecivel)
                .build();
    }

    // Converte para ProdutoRequest genérico (se necessário)
    public ProdutoDTO.ProdutoRequest toProdutoRequest() {
        return new ProdutoDTO.ProdutoRequest( // USANDO O TIPO ANINHADO
                this.nome,
                this.marca,
                this.preco,
                this.quantidade,
                this.perecivel,
                null, // perfume
                ProdutoDTO.TipoProduto.ALIMENTO
        );
    }

    // Métodos with para imutabilidade
    public AlimentoRequest withNome(String novoNome) {
        return new AlimentoRequest(novoNome, marca, preco, quantidade, perecivel);
    }

    public AlimentoRequest withMarca(String novaMarca) {
        return new AlimentoRequest(nome, novaMarca, preco, quantidade, perecivel);
    }

    public AlimentoRequest withPreco(BigDecimal novoPreco) {
        return new AlimentoRequest(nome, marca, novoPreco, quantidade, perecivel);
    }

    public AlimentoRequest withQuantidade(Integer novaQuantidade) {
        return new AlimentoRequest(nome, marca, preco, novaQuantidade, perecivel);
    }

    public AlimentoRequest withPerecivel(Boolean novoPerecivel) {
        return new AlimentoRequest(nome, marca, preco, quantidade, novoPerecivel);
    }
}
