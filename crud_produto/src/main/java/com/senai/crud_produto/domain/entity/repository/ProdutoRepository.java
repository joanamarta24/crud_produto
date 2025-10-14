package com.senai.crud_produto.domain.entity.repository;

import com.senai.crud_produto.domain.entity.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  ProdutoRepository extends JpaRepository<Produto,String> {

}
