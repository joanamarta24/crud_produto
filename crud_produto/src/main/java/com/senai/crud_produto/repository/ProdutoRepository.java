package com.senai.crud_produto.repository;

import com.senai.crud_produto.dto.ProdutoDTO;
import com.senai.crud_produto.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
@
public class ProdutoRepository extends JpaRepository<Produto,String> {
    return ProdutoDTO.ProdutoResponse.fromEntity(
            repository.save(dto.toEntity)
            )
}
