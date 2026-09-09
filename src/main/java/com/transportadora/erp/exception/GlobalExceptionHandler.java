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
public class GlobalExceptionHandler {

    // Captura erros de validação das anotações (@NotBlank, @NotNull, etc.) -> HTTP
    // 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> tratarValidacao(MethodArgumentNotValidException ex) {
        List<String> erros = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ErroResposta resposta = new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação nos campos informados.",
                OffsetDateTime.now(),
                erros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    // Trata apenas exceções explícitas de regra de negócio (HTTP 400)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroResposta> tratarRegraNegocio(IllegalArgumentException ex) {
        ErroResposta resposta = new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                OffsetDateTime.now(),
                List.of());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
    }

    // Captura recursos não encontrados (IDs inexistentes) -> HTTP 404
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroResposta> tratarNaoEncontrado(EntityNotFoundException ex) {
        ErroResposta resposta = new ErroResposta(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                OffsetDateTime.now(),
                List.of());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
    }

    // Captura violações de chaves únicas ou constraints do banco de dados (ex:
    // Placa/CPF duplicados) -> HTTP 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResposta> tratarIntegridadeBanco(DataIntegrityViolationException ex) {
        ErroResposta resposta = new ErroResposta(
                HttpStatus.CONFLICT.value(),
                "Conflito de integridade nos dados (registro duplicado ou vínculo inválido).",
                OffsetDateTime.now(),
                List.of(ex.getMostSpecificCause().getMessage()));

        return ResponseEntity.status(HttpStatus.CONFLICT).body(resposta);
    }

    // Captura exceções genéricas / não tratadas (evita expor o stack trace padrão
    // no HTTP 500) -> HTTP 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> tratarErroGenerico(Exception ex) {
        ErroResposta resposta = new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro interno no servidor.",
                OffsetDateTime.now(),
                List.of(ex.getMessage()));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resposta);
    }
}