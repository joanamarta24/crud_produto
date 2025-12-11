package com.senai.crud_produto.applicatio.dto.dto;

import com.senai.crud_produto.applicatio.dto.dto.Enum.TipoMovimentacao;
import lombok.Builder;

public class MovimentacaoDTO {
   public record MovimentacaoRequest(
           String idProduto,
           TipoMovimentacao tipoMovimentacao,
           int quantidade
   ){
       public MovimentacaoRequest{
           if (quantidade <= 0){
               throw new RuntimeException("Quantidade deve ser maior que zero");
           }
       }
   }
    public record MovimentacaoResponse(
            String idProduto,
            String nomeProduto,
            TipoMovimentacao tipoMovimentacao,
            int quantidadeMovimentada,
            int novaQuantidadeEstoque
    ) {
        public static MovimentacaoResponse fromEntityAndRequest(
                com.senai.crud_produto.domain.entity.entity.Produto produto,
                MovimentacaoRequest request,
                int novaQuantidade
        ) {
            return new MovimentacaoResponse(
                    produto.getId().toString(),
                    produto.getNome(),
                    request.tipoMovimentacao(),
                    request.quantidade(),
                    novaQuantidade
            );
        }
    }
}
