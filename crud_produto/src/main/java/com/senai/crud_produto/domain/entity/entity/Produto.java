package com.senai.crud_produto.domain.entity.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class Produto {
  @Id
  @GeneratedValue(strategy =GenerationType.UUID)
  private Long id;
  private String nome;
  private BigDecimal preco;
  private Integer quantidade;
  private String marca;


  }
