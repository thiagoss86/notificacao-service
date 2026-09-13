package com.eventos.notificacao_service.controller;

import com.eventos.notificacao_service.dto.NotificacaoRequest;
import com.eventos.notificacao_service.dto.NotificacaoResponse;
import com.eventos.notificacao_service.entity.StatusNotificacao;
import com.eventos.notificacao_service.entity.TipoNotificacao;
import com.eventos.notificacao_service.service.NotificacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
@RequiredArgsConstructor
@Tag(
        name = "Notificações",
        description = "Operações relacionadas ao gerenciamento de notificações"
)
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    @PostMapping
    @Operation(
            summary = "Criar notificação",
            description = "Cria uma nova notificação com status inicial PENDENTE"
    )
    public ResponseEntity<NotificacaoResponse> criar(
            @Valid @RequestBody NotificacaoRequest request) {

        var resposta = notificacaoService.criar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resposta);
    }

    @GetMapping
    @Operation(
            summary = "Listar notificações",
            description = "Retorna todas as notificações"
    )
    public ResponseEntity<List<NotificacaoResponse>> listarTodas() {

        var notificacoes = notificacaoService.listarTodos();

        return ResponseEntity.ok(notificacoes);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar notificação por ID",
            description = "Retorna uma notificação especifica pelo seu identificador"
    )
    public ResponseEntity<NotificacaoResponse> buscarPorId(
            @PathVariable Long id) {

        var resposta = notificacaoService.buscarPorId(id);

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/buscar/participante/{id}")
    @Operation(
            summary = "Buscar notificações por participante",
            description = "Retorna todas as notificações de uma participante"
    )
    public ResponseEntity<List<NotificacaoResponse>> buscarPorParticipante(
            @PathVariable Long id) {

        var resposta = notificacaoService.buscarPorParticipante(id);

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/buscar/status")
    @Operation(
            summary = "Buscar notificações por status",
            description = "Retorna notificações filtradas pelo status informado"
    )
    public ResponseEntity<List<NotificacaoResponse>> buscarPorStatus(
            @RequestParam StatusNotificacao status) {

        var resposta = notificacaoService.buscarPorStatus(status);

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/buscar/tipo")
    @Operation(
            summary = "Buscar notificações por tipo",
            description = "Retorna notificações filtradas pelo tipo informado"
    )
    public ResponseEntity<List<NotificacaoResponse>> buscarPorTipo(
            @RequestParam TipoNotificacao tipo) {

        var resposta = notificacaoService.buscarPorTipo(tipo);

        return ResponseEntity.ok(resposta);
    }

    @PatchMapping("/{id}/enviada")
    @Operation(
            summary = "Marcar notificação como ENVIADA",
            description = "Altera o status da notificação para ENVIADA"
    )
    public ResponseEntity<NotificacaoResponse> marcarComEnviada(
            @PathVariable long id) {

        return ResponseEntity.ok(notificacaoService.marcarComoEnviada(id));
    }

    @PatchMapping("/{id}/falha")
    @Operation(
            summary = "Marcar notificação com FALHA",
            description = "Altera o status da notificação para FALHA"
    )
    public ResponseEntity<NotificacaoResponse> marcarComFalha(
            @PathVariable long id) {

        return ResponseEntity.ok(notificacaoService.marcarComoFalha(id));
    }
}
