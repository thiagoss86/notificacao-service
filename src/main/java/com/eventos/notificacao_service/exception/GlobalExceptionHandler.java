package com.eventos.notificacao_service.exception;

import com.eventos.notificacao_service.dto.ErroResponse;
import com.eventos.notificacao_service.dto.ErroValidacaoResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> tratarRecursoNaoEncontrado(
            RecursoNaoEncontradoException ex,
            HttpServletRequest request) {

        return criarResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroResponse> tratarRegraNegocio(
            RegraNegocioException ex,
            HttpServletRequest request) {

        return criarResponse(
                HttpStatus.CONFLICT,
                ex.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroValidacaoResponse> tratarValidacao(
            MethodArgumentNotValidException ex) {

        Map<String, String> detalhes = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> {
                    detalhes.put(
                            fieldError.getField(),
                            fieldError.getDefaultMessage());
                });

        ErroValidacaoResponse resposta = new ErroValidacaoResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Dados inválidos.",
                detalhes
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resposta);
    }

    private ResponseEntity<ErroResponse> criarResponse(HttpStatus status, String message) {

        ErroResponse resposta = new ErroResponse(
                LocalDateTime.now(),
                status.value(),
                message
        );

        return ResponseEntity
                .status(status)
                .body(resposta);
    }
}
