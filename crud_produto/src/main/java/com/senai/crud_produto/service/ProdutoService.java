package com.senai.crud_produto.service;

import com.senai.crud_produto.dto.ProdutoDTO;
import com.senai.crud_produto.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private  final ProdutoRepository repository;
    public ProdutoDTO.ProdutoResponse cadastrarProduto(ProdutoDTO.ProdutoRequest dto) {

        return ProdutoDTO.ProdutoResponse.fromEntity{
            repository.save(dto.toEntity())
        };
    }
    public List<ProdutoDTO.ProdutoResponse>listarProdutos(){
        return repository.findAll()
                .stream()
                .map(ProdutoDTO.ProdutoResponse::fromEntity)
                .toList();
    }
    public ProdutoDTO.ProdutoResponse buscarPorId(String id){
        return repository.findById(id)
                .map(ProdutoDTO.ProdutoResponse::fromEntity)
                .orElseThrow(()-> new RuntimeException("Produto não encontrado"));
    }
}
