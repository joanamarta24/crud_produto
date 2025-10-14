package com.senai.crud_produto.interface_ui_controller;

import com.senai.crud_produto.dto.ProdutoDTO;
import com.senai.crud_produto.entity.Produto;
import com.senai.crud_produto.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService service;


    @PostMapping
    public ResponseEntity<ProdutoDTO.ProdutoResponse> cadastrarProduto() {
        Produto produtoCadastrado = service.cadastrarProduto();
        return ResponseEntity.created(URI.create("/api/produtos" + produtoCadastrado.getId()));
        .body(ProdutoDTO.ProdutoResponse);
    }

    @GetMapping
    public ResponseEntity<ProdutoDTO.ProdutoResponse> cadastrarProduto(@RequestBody ProdutoDTO.ProdutoRequest dto) {
        ProdutoDTO.ProdutoResponse.produtoCadastrado = service.cadastrarProduto(dto);
        return ResponseEntity.created(URI.create("/api/produtos" + produtoCadastrado.id()))
                .body(produtoCadastrado);
    }

    @GetMapping
    public ResponseEntity<List ProdutoDTO.ProdutoDTO.ProdutoResponse>>

    listarProdutos() {
        return ResponseEntity.ok(service.listarProdutos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO.ProdutoResponse> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO.ProdutoResponse> atualizarProduto(@PathVariable String id, @RequestBody ProdutoDTO.ProdutoRequest dto)
        return ResponseEntity.ok(service.atualizarProduto(id,dto));

   }
   @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable String id) {
    service.deletarProduto(id);
    return ResponseEntity.noContent().build();
}

