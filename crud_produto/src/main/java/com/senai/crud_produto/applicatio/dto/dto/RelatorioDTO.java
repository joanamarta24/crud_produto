package com.senai.crud_produto.applicatio.dto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RelatorioDTO {

    public record EstoqueResponse(
            int totalProdutos,
            int totalAlimentos,
            int totalLimpeza,
            BigDecimal valorTotalEstoque,
            LocalDateTime dataGeracao
    ) {}

    public record MovimentacaoDiariaResponse(
            LocalDateTime data,
            int totalEntradas,
            int totalSaidas,
            BigDecimal valorMovimentado
    ) {}

    public record ProdutoEstoqueBaixoResponse(
            String idProduto,
            String nome,
            String tipo,
            int quantidadeAtual,
            int quantidadeMinima
    ) {}
}