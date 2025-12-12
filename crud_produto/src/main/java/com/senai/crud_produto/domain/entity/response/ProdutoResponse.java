package com.senai.crud_produto.domain.entity.response;

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
        LIMPEZA,
        ALIMENTO
    }
}

