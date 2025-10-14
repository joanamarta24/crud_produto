package com.senai.crud_produto.service;

import com.senai.crud_produto.dto.ProdutoDTO;
import com.senai.crud_produto.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private  final ProdutoRepository repository;
    public Object cadastrarProduto(ProdutoDTO.ProdutoRequest dto) {
        return ProdutoDTO.ProdutoResponse.fromEntity{
            repository.save(dto.toEntity())
        };
    }
}
