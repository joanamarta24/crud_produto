package com.senai.crud_produto.applicatio.dto.service;

import com.senai.crud_produto.applicatio.dto.dto.ProdutoDTO;
import com.senai.crud_produto.domain.entity.entity.Alimentos;
import com.senai.crud_produto.domain.entity.entity.Limpeza;
import com.senai.crud_produto.domain.entity.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static sun.awt.image.MultiResolutionCachedImage.map;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private  final ProdutoRepository repository;
    public ProdutoDTO.ProdutoResponse cadastrarProduto(ProdutoDTO.ProdutoRequest dto) {

        return ProdutoDTO.ProdutoResponse.fromEntity(){
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
   public ProdutoDTO.ProdutoResponse atualizarProduto(String id, ProdutoDTO.ProdutoResponse dto){
       .map(produto -> {)
           produto.setNome(dto.nome());
           produto.setMarca(dto.marca());
           produto.setPreco(dto.preco());
           produto.setQuantidade(dto.quantidade());
           if (produto instanceof Limpeza limpeza){
                limpeza.setPerfume(dto.perfume());
           } else if (produto instanceof Alimentos alimentos) {
               alimentos.setPerecivel(dto.perecivel());

           }
           return ProdutoDTO.ProdutoResponse.fromEntity(repository.save(produto));
       })
               .orElseThrow(()-> new RuntimeException("Produto não encontrado"));
    }
    public void deletarProduto(String id){
        repository.deleteById(id);
    }
}

