package com.eventos.notificacao_service.dto;

import com.eventos.notificacao_service.entity.TipoNotificacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NotificacaoRequest(

        @NotNull(message = "O participante é obrigatório.")
        Long participanteId,

        @NotNull(message = "O tipo da notificação é obrigatório.")
        TipoNotificacao tipo,

        @NotBlank(message = "A mensagem da notificação é obrigatoria.")
        @Size(
                max = 100,
                message = "A mensagem deve conter no máximo 100 caracteres."
        )
        String mensagem
) {
}
