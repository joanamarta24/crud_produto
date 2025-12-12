package com.senai.crud_produto.interface_ui_controller;

import com.senai.crud_produto.request.LimpezaRequest;
import com.senai.crud_produto.dto.MovimentacaoDTO;
import com.senai.crud_produto.dto.ProdutoDTO;
import com.senai.crud_produto.dto.service.ProdutoService;
import com.senai.crud_produto.request.AlimentoRequest;
import com.senai.crud_produto.request.ProdutoRequest;
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
    public ResponseEntity<ProdutoDTO.ProdutoResponse> criarLimpeza(
            @RequestBody LimpezaRequest request) {
        // É necessário converter LimpezaRequest para ProdutoDTO.ProdutoRequest
        ProdutoDTO.ProdutoRequest produtoRequest = request.toProdutoRequest();
        ProdutoDTO.ProdutoResponse response = service.cadastrarProduto(produtoRequest);

        return ResponseEntity.created(URI.create("/api/produtos/" + response.getId())) // CORRIGIDO: usa getId()
                .body(response);
    }
    @PostMapping("/alimento")
    public ResponseEntity<ProdutoDTO.ProdutoResponse> criarAlimento(
            @RequestBody AlimentoRequest request) {
        // É necessário converter AlimentoRequest para ProdutoDTO.ProdutoRequest
        ProdutoDTO.ProdutoRequest produtoRequest = request.toProdutoRequest();
        ProdutoDTO.ProdutoResponse response = service.cadastrarProduto(produtoRequest);

        return ResponseEntity.created(URI.create("/api/produtos/" + response.getId())) // CORRIGIDO: usa getId()
                .body(response);
    }

    @PostMapping("/alimento")
    public ResponseEntity<ProdutoDTO.ProdutoResponse> criarAlimento(
            @RequestBody AlimentoRequest request) {
        ProdutoDTO.ProdutoResponse response = service.cadastrarProduto(request);
        return ResponseEntity.created(URI.create("/api/produtos/" + response.id()))
                .body(response);
    }


    // POST para cadastrar
    @PostMapping
    public ResponseEntity<ProdutoDTO.ProdutoResponse> cadastrarProduto(@RequestBody ProdutoDTO.ProdutoRequest dto) {
        ProdutoDTO.ProdutoResponse produtoCadastrado = service.cadastrarProduto(dto);
        return ResponseEntity.created(URI.create("/api/produtos/" + produtoCadastrado.id()))
                .body(produtoCadastrado);
    }

    // GET para listar todos
    @GetMapping
    public ResponseEntity<List<ProdutoDTO.ProdutoResponse>> listarProdutos() {
        return ResponseEntity.ok(service.listarProdutos());
    }

    // GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO.ProdutoResponse> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // PUT para atualizar
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO.ProdutoResponse> atualizarProduto(
            @PathVariable String id,
            @RequestBody ProdutoDTO.ProdutoRequest dto) {
        return ResponseEntity.ok(service.atualizarProduto(id, dto));
    }

    // DELETE para remover
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable String id) {
        service.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

    // Método adicional para movimentação (se necessário)
    @PostMapping("/{id}/movimentacao")
    public ResponseEntity<MovimentacaoDTO> registrarMovimentacao(
            @PathVariable String id,
            @RequestBody MovimentacaoDTO dto) {
        // Implementação da movimentação
        return ResponseEntity.ok(service.registrarMovimentacao(id, dto));
    }
}