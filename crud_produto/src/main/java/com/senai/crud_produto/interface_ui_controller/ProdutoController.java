package com.senai.crud_produto.interface_ui_controller;

import com.senai.crud_produto.dto.ProdutoDTO;
import com.senai.crud_produto.entity.Produto;
import com.senai.crud_produto.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping ("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService service;



    @PostMapping
    public ResponseEntity<ProdutoDTO.ProdutoResponse> cadastrarProduto(){
        Produto produtoCadastrado = service.cadastrarProduto();
        return ResponseEntity.created(URI.create("/api/produtos"+produtoCadastrado.getId()));
        .body(ProdutoDTO.ProdutoResponse);
    }
   @GetMapping
    public  ResponseEntity<ProdutoDTO.ProdutoResponse>cadastrarProduto(@RequestBody ProdutoDTO.ProdutoRequest dto){
        ProdutoDTO.ProdutoResponse.produtoCadastrado = service.cadastrarProduto(dto);
        return ResponseEntity.created(URI.create("/api/produtos"+produtoCadastrado.id()))
                .body(produtoCadastrado);
   }
}
