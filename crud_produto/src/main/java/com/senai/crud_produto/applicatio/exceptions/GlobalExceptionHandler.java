package com.senai.crud_produto.applicatio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleProdutoNaoEncontrado(ProdutoNaoEncontradoException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Produto não encontrado",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<ErrorResponse> handleEstoqueInsuficiente(EstoqueInsuficienteException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Estoque insuficiente",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno do servidor",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public record ErrorResponse(
            int status,
            String error,
            String message
    ) {
    }

    public class ProdutoNaoEncontradoException extends RuntimeException {
        public ProdutoNaoEncontradoException(String id) {
            super("Produto com ID" + id + "nao encontrado");
        }
    }

    public class EstoqueInsuficienteException extends RuntimeException {
        public EstoqueInsuficienteException(int disponivel, int solicitado) {
            super(String.format("Estoque insuficiente. Disponível: %d, Solicitado: %d", disponivel, solicitado));
        }
    }

    public class TipoProdutoInvalidoException extends RuntimeException {
        public TipoProdutoInvalidoException() {
            super("Tipo de produto inválido");
        }
    }

    public void TipoProdutoInvalidoException(String message) {
        super(message);
    }
}
