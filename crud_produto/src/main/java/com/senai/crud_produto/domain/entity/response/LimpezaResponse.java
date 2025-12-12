package com.senai.crud_produto.domain.entity.response;

import com.senai.crud_produto.domain.entity.entity.Limpeza;

import java.math.BigDecimal;

public class LimpezaResponse {

    public record LimpezaResponseDTO(
            String id,
            String nome,
            String marca,
            BigDecimal preco,
            Integer quantidade,
            Boolean perfume
    ) {
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
}