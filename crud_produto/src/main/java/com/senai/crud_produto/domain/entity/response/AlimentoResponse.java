package com.senai.crud_produto.applicatio.dto.dto;

import com.senai.crud_produto.domain.entity.entity.Alimentos;

import java.math.BigDecimal;

public class AlimentoResponse {

    // Record principal para resposta de Alimento
    public record AlimentoResponseDTO(
            String id,
            String nome,
            String marca,
            BigDecimal preco,
            Integer quantidade,
            Boolean perecivel
    ) {
        // Método factory para criar a partir da entidade
        public static AlimentoResponseDTO fromEntity(Alimentos alimento) {
            return new AlimentoResponseDTO(
                    alimento.getId() != null ? alimento.getId().toString() : null,
                    alimento.getNome(),
                    alimento.getMarca(),
                    alimento.getPreco(),
                    alimento.getQuantidade(),
                    alimento.getPerecivel()
            );
        }

        // Método para converter para String (opcional)
        @Override
        public String toString() {
            return String.format("Alimento[id=%s, nome=%s, marca=%s, preco=%.2f, quantidade=%d, perecivel=%s]",
                    id, nome, marca, preco, quantidade, perecivel);
        }
    }

    // Record para criação de Alimento (Request)
    public record AlimentoCreateRequest(
            String nome,
            String marca,
            BigDecimal preco,
            Integer quantidade,
            Boolean perecivel
    ) {
        // Validações no construtor compacto
        public AlimentoCreateRequest {
            if (nome == null || nome.trim().isEmpty()) {
                throw new IllegalArgumentException("Nome é obrigatório");
            }
            if (quantidade == null || quantidade < 0) {
                throw new IllegalArgumentException("Quantidade não pode ser negativa");
            }
            if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Preço não pode ser negativo");
            }
        }

        // Método para converter para entidade
        public Alimentos toEntity() {
            return Alimentos.builder()
                    .nome(this.nome)
                    .marca(this.marca)
                    .preco(this.preco)
                    .quantidade(this.quantidade)
                    .perecivel(this.perecivel)
                    .build();
        }
    }

    // Record para atualização de Alimento (Update Request)
    public record AlimentoUpdateRequest(
            String nome,
            String marca,
            BigDecimal preco,
            Integer quantidade,
            Boolean perecivel
    ) {
        // Validações no construtor compacto
        public AlimentoUpdateRequest {
            if (quantidade != null && quantidade < 0) {
                throw new IllegalArgumentException("Quantidade não pode ser negativa");
            }
            if (preco != null && preco.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Preço não pode ser negativo");
            }
        }

        // Método para aplicar atualizações em uma entidade existente
        public void applyTo(Alimentos alimento) {
            if (this.nome != null) {
                alimento.setNome(this.nome);
            }
            if (this.marca != null) {
                alimento.setMarca(this.marca);
            }
            if (this.preco != null) {
                alimento.setPreco(this.preco);
            }
            if (this.quantidade != null) {
                alimento.setQuantidade(this.quantidade);
            }
            if (this.perecivel != null) {
                alimento.setPerecivel(this.perecivel);
            }
        }
    }

    // Record para listagem resumida de Alimentos
    public record AlimentoResumoResponse(
            String id,
            String nome,
            BigDecimal preco,
            Integer quantidade,
            Boolean perecivel
    ) {
        public static AlimentoResumoResponse fromEntity(Alimentos alimento) {
            return new AlimentoResumoResponse(
                    alimento.getId().toString(),
                    alimento.getNome(),
                    alimento.getPreco(),
                    alimento.getQuantidade(),
                    alimento.getPerecivel()
            );
        }
    }

    // Record para movimentação de estoque de Alimentos
    public record AlimentoMovimentacaoResponse(
            String idAlimento,
            String nomeAlimento,
            Integer quantidadeAnterior,
            Integer quantidadeMovimentada,
            Integer novaQuantidade,
            Boolean perecivel
    ) {
        public static AlimentoMovimentacaoResponse fromMovimentacao(
                Alimentos alimento,
                Integer quantidadeMovimentada,
                Integer novaQuantidade
        ) {
            return new AlimentoMovimentacaoResponse(
                    alimento.getId().toString(),
                    alimento.getNome(),
                    alimento.getQuantidade() - quantidadeMovimentada,
                    quantidadeMovimentada,
                    novaQuantidade,
                    alimento.getPerecivel()
            );
        }
    }

    // Métodos utilitários
    public static class AlimentoUtils {

        // Verifica se o alimento está vencido (exemplo)
        public static boolean estaVencido(Alimentos alimento) {
            // Lógica para verificar validade
            return false;
        }

        // Calcula valor total em estoque
        public static BigDecimal calcularValorEstoque(Alimentos alimento) {
            if (alimento.getPreco() == null || alimento.getQuantidade() == null) {
                return BigDecimal.ZERO;
            }
            return alimento.getPreco().multiply(BigDecimal.valueOf(alimento.getQuantidade()));
        }

        // Formata dados do alimento
        public static String formatarDados(Alimentos alimento) {
            return String.format(
                    "%s - %s (R$ %.2f) - %d unidades - %s",
                    alimento.getNome(),
                    alimento.getMarca(),
                    alimento.getPreco(),
                    alimento.getQuantidade(),
                    alimento.getPerecivel() ? "Perecível" : "Não Perecível"
            );
        }
    }
}