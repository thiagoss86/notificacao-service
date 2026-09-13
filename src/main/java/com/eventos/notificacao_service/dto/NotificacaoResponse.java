package com.eventos.notificacao_service.dto;

import com.eventos.notificacao_service.entity.StatusNotificacao;
import com.eventos.notificacao_service.entity.TipoNotificacao;

import java.time.LocalDateTime;

public record NotificacaoResponse(

        Long id,
        Long participanteId,
        TipoNotificacao tipo,
        String mensagem,
        LocalDateTime data,
        StatusNotificacao status
) {
}
