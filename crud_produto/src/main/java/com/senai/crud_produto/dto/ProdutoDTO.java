package com.senai.crud_produto.dto;

import com.senai.crud_produto.domain.entity.entity.Alimentos;
import com.senai.crud_produto.domain.entity.entity.Limpeza;
import com.senai.crud_produto.domain.entity.entity.Produto;

import java.math.BigDecimal;

public class ProdutoDTO {

    public enum TipoProduto {
        LIMPEZA,
        ALIMENTO
    }

   public static class ProdutoResponse{
        private String id;
       private String nome;
       private  String marca;
       private BigDecimal preco;
       private Integer quantidade;
       private Boolean perecivel;
       private String perfume;
       private TipoProduto tipo;

       public ProdutoResponse(String id, String nome, String marca, BigDecimal preco, Integer quantidade, Boolean perecivel, String perfume, TipoProduto tipo) {
           this.id = id;
           this.nome = nome;
           this.marca = marca;
           this.preco = preco;
           this.quantidade = quantidade;
           this.perecivel = perecivel;
           this.perfume = perfume;
           this.tipo = tipo;
       }
          //Getters
       public String getId() {return id;}
       public String getNome() {return nome;}
       public String getMarca() {return marca;}
       public BigDecimal getPreco() {return preco;}
       public Integer getQuantidade() {return quantidade;}
       public Boolean getPerecivel() {return perecivel;}
       public String getPerfume() {return perfume;}
       public TipoProduto getTipo() {return tipo;}
       //Setters
       public void setId(String id) {this.id = id;}
       public void setNome(String nome) {this.nome = nome;}
       public void setMarca(String marca) {this.marca = marca;}
       public void setPreco(BigDecimal preco) {this.preco = preco;}
       public void setQuantidade(Integer quantidade) {this.quantidade = quantidade;}
       public void setPerecivel(Boolean perecivel) {this.perecivel = perecivel;}
       public void setPerfume(String perfume) {this.perfume = perfume;}
       public void setTipo(TipoProduto tipo) {this.tipo = tipo;}
   }
    public static ProdutoResponse fromEntity(Produto produto){
        if (produto instanceof Alimentos alimentos){
            return  new ProdutoResponse(
                    alimentos.getId().toString(),
                    alimentos.getNome(),
                    alimentos.getMarca(),
                    alimentos.getPreco(),
                    alimentos.getQuantidade(),
                    alimentos.getPerecivel(),
                    null, // Limpeza
                    TipoProduto.ALIMENTO
            );
            // ADICIONAR: Verifica se a entidade é do tipo Limpeza
        } else if (produto instanceof Limpeza limpeza) {
            return new ProdutoResponse(
                    limpeza.getId().toString(),
                    limpeza.getNome(),
                    limpeza.getMarca(),
                    limpeza.getPreco(),
                    limpeza.getQuantidade(),
                    null, // Alimento
                    limpeza.getPerfume(),
                    TipoProduto.LIMPEZA
            );
        } else {
            throw new RuntimeException("Tipo de produto inváçido");
        }
    }

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
    }
}
