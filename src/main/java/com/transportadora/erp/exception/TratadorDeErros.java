package com.transportadora.erp.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {

    // 1. Trata erros de validação dos DTOs (@Valid / @NotBlank / @Pattern / etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> tratarErroValidacao(MethodArgumentNotValidException ex) {
        List<String> detalhes = ex.getFieldErrors()
                .stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .toList();

        ErroResposta erro = new ErroResposta(
            HttpStatus.BAD_REQUEST.value(),
            "Erro de validação nos campos informados.",
            OffsetDateTime.now(),
            detalhes
        );

        return ResponseEntity.badRequest().body(erro);
    }

    // 2. Trata recursos não encontrados (404)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroResposta> tratarErro404(EntityNotFoundException ex) {
        ErroResposta erro = new ErroResposta(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            OffsetDateTime.now(),
            null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // 3. Trata exceções de regras de negócio (IllegalArgumentException e RuntimeException)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroResposta> tratarRegraDeNegocio(IllegalArgumentException ex) {
        ErroResposta erro = new ErroResposta(
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage(),
            OffsetDateTime.now(),
            null
        );

        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroResposta> tratarRuntimeException(RuntimeException ex) {
        ErroResposta erro = new ErroResposta(
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage(),
            OffsetDateTime.now(),
            null
        );

        return ResponseEntity.badRequest().body(erro);
    }

    // 4. Trata violações de chave única no banco de dados (CPF, CNPJ, Placa ou Rastreio duplicados)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResposta> tratarErroDuplicidade(DataIntegrityViolationException ex) {
        ErroResposta erro = new ErroResposta(
            HttpStatus.CONFLICT.value(),
            "Já existe um registro cadastrado com estes dados (CPF, CNPJ, Placa ou Código de Rastreio duplicado).",
            OffsetDateTime.now(),
            null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }
}