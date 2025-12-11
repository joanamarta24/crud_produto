package com.senai.crud_produto.applicatio.dto.service;

import com.senai.crud_produto.applicatio.dto.dto.AlimentoResponse;
import com.senai.crud_produto.applicatio.dto.MovimentacaoDTO;
import com.senai.crud_produto.applicatio.dto.ProdutoDTO;
import com.senai.crud_produto.domain.entity.entity.Alimentos;
import com.senai.crud_produto.domain.entity.entity.Limpeza;
import com.senai.crud_produto.domain.entity.entity.Produto;
import com.senai.crud_produto.domain.entity.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProdutoService {

    private final ProdutoRepository repository;

    @Transactional
    public ProdutoDTO.ProdutoResponse cadastrarProduto(ProdutoDTO.ProdutoRequest dto) {
        // Validação básica
        if (dto.nome() == null || dto.nome().trim().isEmpty()) {
            throw new RuntimeException("Nome do produto é obrigatório");
        }
        if (dto.quantidade() == null || dto.quantidade() < 0) {
            throw new RuntimeException("Quantidade não pode ser negativa");
        }

        Produto produtoSalvo = repository.save(dto.toEntity());
        return ProdutoDTO.ProdutoResponse.fromEntity(produtoSalvo);
    }

    @Transactional(readOnly = true)
    public List<ProdutoDTO.ProdutoResponse> listarProdutos() {
        return repository.findAll()
                .stream()
                .map(ProdutoDTO.ProdutoResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProdutoDTO.ProdutoResponse buscarPorId(String id) {
        return repository.findById(id)
                .map(ProdutoDTO.ProdutoResponse::fromEntity)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    @Transactional
    public ProdutoDTO.ProdutoResponse atualizarProduto(String id, ProdutoDTO.ProdutoRequest dto) {
        return repository.findById(id)
                .map(produto -> {
                    // Verifica se não está tentando mudar o tipo do produto
                    if ((produto instanceof Limpeza && dto.tipo() != ProdutoDTO.TipoProduto.LIMPEZA) ||
                            (produto instanceof Alimentos && dto.tipo() != ProdutoDTO.TipoProduto.ALIMENTO)) {
                        throw new RuntimeException("Não é possível alterar o tipo do produto");
                    }

                    produto.setNome(dto.nome());
                    produto.setMarca(dto.marca());
                    produto.setPreco(dto.preco());
                    produto.setQuantidade(dto.quantidade());

                    // Apenas atualiza campos específicos se não forem nulos
                    if (produto instanceof Limpeza limpeza) {
                        if (dto.perfume() != null) {
                            limpeza.setPerfume(dto.perfume());
                        }
                    } else if (produto instanceof Alimentos alimentos) {
                        if (dto.perecivel() != null) {
                            alimentos.setPerecivel(dto.perecivel());
                        }
                    }

                    Produto produtoAtualizado = repository.save(produto);
                    return ProdutoDTO.ProdutoResponse.fromEntity(produtoAtualizado);
                })
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    @Transactional
    public void deletarProduto(String id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado para exclusão");
        }
        repository.deleteById(id);
    }

    @Transactional
    public MovimentacaoDTO.MovimentacaoResponse movimentarEstoque(MovimentacaoDTO.MovimentacaoRequest dto) {
        if (dto.quantidade() <= 0) {
            throw new RuntimeException("Quantidade deve ser maior que zero");
        }

        return repository.findById(dto.idProduto())
                .map(produto -> {
                    int novaQuantidade = switch (dto.tipoMovimentacao()) {
                        case ENTRADA -> produto.getQuantidade() + dto.quantidade();
                        case SAIDA -> {
                            if (produto.getQuantidade() < dto.quantidade()) {
                                throw new RuntimeException(
                                        String.format("Quantidade insuficiente em estoque. Disponível: %d, Solicitado: %d",
                                                produto.getQuantidade(), dto.quantidade())
                                );
                            }
                            yield produto.getQuantidade() - dto.quantidade();
                        }
                    };

                    produto.setQuantidade(novaQuantidade);
                    Produto produtoAtualizado = repository.save(produto);

                    // CORREÇÃO: Use o construtor diretamente ou crie o método factory
                    // Opção 1: Construtor direto
                    return new MovimentacaoDTO.MovimentacaoResponse(
                            produtoAtualizado.getId().toString(),
                            produtoAtualizado.getNome(),
                            dto.tipoMovimentacao(),
                            dto.quantidade(),
                            novaQuantidade
                    );

                    // Opção 2: Se quiser usar método factory, primeiro crie ele no DTO:
                    return MovimentacaoDTO.MovimentacaoResponse.fromMovimentacao(
                        produtoAtualizado,
                        dto.tipoMovimentacao(),
                       dto.quantidade(),
                        novaQuantidade
                     );
                })
                .orElseThrow(() -> new RuntimeException("Produto não encontrado para movimentação"));
    }

    // Método para cadastrar alimento específico
    public AlimentoResponse.AlimentoResponseDTO cadastrarAlimento(AlimentoResponse.AlimentoCreateRequest request) {
        // Validações
        if (request.nome() == null || request.nome().trim().isEmpty()) {
            throw new RuntimeException("Nome do alimento é obrigatório");
        }
        if (request.quantidade() < 0) {
            throw new RuntimeException("Quantidade não pode ser negativa");
        }
        if (request.preco().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Preço não pode ser negativo");
        }

        // Converte request para entidade e salva
        Alimentos alimento = request.toEntity();
        Alimentos alimentoSalvo = repository.save(alimento);

        // Retorna DTO de resposta
        return AlimentoResponse.AlimentoResponseDTO.fromEntity(alimentoSalvo);
    }

    // Método para buscar alimento por ID
    public AlimentoResponse.AlimentoResponseDTO buscarAlimentoPorId(String id) {
        return repository.findById(id)
                .filter(Alimentos.class::isInstance) // Filtra apenas Alimentos
                .map(Alimentos.class::cast) // Faz o cast para Alimentos
                .map(AlimentoResponse.AlimentoResponseDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Alimento não encontrado com ID: " + id));
    }

    // Método para atualizar alimento
    public AlimentoResponse.AlimentoResponseDTO atualizarAlimento(String id, AlimentoResponse.AlimentoUpdateRequest request) {
        return repository.findById(id)
                .filter(Alimentos.class::isInstance)
                .map(Alimentos.class::cast)
                .map(alimento -> {
                    // Aplica as atualizações apenas se os campos não forem null
                    if (request.nome() != null) {
                        alimento.setNome(request.nome());
                    }
                    if (request.marca() != null) {
                        alimento.setMarca(request.marca());
                    }
                    if (request.preco() != null) {
                        alimento.setPreco(request.preco());
                    }
                    if (request.quantidade() != null) {
                        alimento.setQuantidade(request.quantidade());
                    }
                    if (request.perecivel() != null) {
                        alimento.setPerecivel(request.perecivel());
                    }

                    Alimentos alimentoAtualizado = repository.save(alimento);
                    return AlimentoResponse.AlimentoResponseDTO.fromEntity(alimentoAtualizado);
                })
                .orElseThrow(() -> new RuntimeException("Alimento não encontrado com ID: " + id));
    }

    // Método para listar todos os alimentos
    public List<AlimentoResponse.AlimentoResponseDTO> listarAlimentos() {
        return repository.findAll()
                .stream()
                .filter(Alimentos.class::isInstance)
                .map(Alimentos.class::cast)
                .map(AlimentoResponse.AlimentoResponseDTO::fromEntity)
                .toList();
    }

    // Método para deletar alimento
    public void deletarAlimento(String id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Alimento não encontrado com ID: " + id);
        }

        // Verifica se realmente é um alimento antes de deletar
        repository.findById(id)
                .filter(Alimentos.class::isInstance)
                .orElseThrow(() -> new RuntimeException("O produto com ID " + id + " não é um alimento"));

        repository.deleteById(id);
    }