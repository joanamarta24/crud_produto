package com.senai.crud_produto.domain.entity.response;

import com.senai.crud_produto.domain.entity.entity.Limpeza;

import java.math.BigDecimal;

// Interface para respostas de produto
public interface ProdutoResponseDTO {
    String id();
    String nome();
    String marca();
    BigDecimal preco();
    Integer quantidade();
    TipoProduto tipo(); // Usando enum direto aqui

    // Enum interno
    enum TipoProduto {
        LIMPEZA,
        ALIMENTO
    }
}

// Classe concreta para Limpeza
public class LimpezaResponseDTO implements ProdutoResponseDTO {
    private final String id;
    private final String nome;
    private final String marca;
    private final BigDecimal preco;
    private final Integer quantidade;
    private final Boolean perfume;

    // Construtor
    public LimpezaResponseDTO(String id, String nome, String marca,
                              BigDecimal preco, Integer quantidade, Boolean perfume) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.quantidade = quantidade;
        this.perfume = perfume;
    }

    // Getters
    @Override public String id() { return id; }
    @Override public String nome() { return nome; }
    @Override public String marca() { return marca; }
    @Override public BigDecimal preco() { return preco; }
    @Override public Integer quantidade() { return quantidade; }
    @Override public ProdutoResponseDTO.TipoProduto tipo() {
        return ProdutoResponseDTO.TipoProduto.LIMPEZA;
    }
    public Boolean perfume() {
        return perfume;
    }

    // Método factory
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