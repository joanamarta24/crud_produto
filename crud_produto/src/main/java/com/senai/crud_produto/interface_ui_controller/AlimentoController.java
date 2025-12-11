package com.senai.crud_produto.interface_ui_controller;

import com.senai.crud_produto.applicatio.dto.dto.AlimentoResponse;
import com.senai.crud_produto.applicatio.dto.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/alimentos")
@RequiredArgsConstructor
public class AlimentoController {

    private final ProdutoService service;

    @PostMapping
    public ResponseEntity<AlimentoResponse.AlimentoResponseDTO> criarAlimento(
            @RequestBody AlimentoResponse.AlimentoCreateRequest request) {
        AlimentoResponse.AlimentoResponseDTO response = service.cadastrarAlimento(request);
        return ResponseEntity.created(URI.create("/api/alimentos/" + response.id()))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlimentoResponse.AlimentoResponseDTO> buscarAlimento(@PathVariable String id) {
        AlimentoResponse.AlimentoResponseDTO response = service.buscarAlimentoPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AlimentoResponse.AlimentoResponseDTO>> listarAlimentos() {
        List<AlimentoResponse.AlimentoResponseDTO> alimentos = service.listarAlimentos();
        return ResponseEntity.ok(alimentos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlimentoResponse.AlimentoResponseDTO> atualizarAlimento(
            @PathVariable String id,
            @RequestBody AlimentoResponse.AlimentoUpdateRequest request) {
        AlimentoResponse.AlimentoResponseDTO response = service.atualizarAlimento(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAlimento(@PathVariable String id) {
        service.deletarAlimento(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint adicional para operações específicas de alimentos

    @GetMapping("/pereciveis")
    public ResponseEntity<List<AlimentoResponse.AlimentoResponseDTO>> listarAlimentosPereciveis() {
        List<AlimentoResponse.AlimentoResponseDTO> alimentos = service.listarAlimentos()
                .stream()
                .filter(alimento -> alimento.perecivel() != null && alimento.perecivel())
                .toList();
        return ResponseEntity.ok(alimentos);
    }

    @GetMapping("/nao-pereciveis")
    public ResponseEntity<List<AlimentoResponse.AlimentoResponseDTO>> listarAlimentosNaoPereciveis() {
        List<AlimentoResponse.AlimentoResponseDTO> alimentos = service.listarAlimentos()
                .stream()
                .filter(alimento -> alimento.perecivel() == null || !alimento.perecivel())
                .toList();
        return ResponseEntity.ok(alimentos);
    }
}