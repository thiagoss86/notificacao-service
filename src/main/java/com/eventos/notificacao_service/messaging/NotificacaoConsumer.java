package com.eventos.notificacao_service.messaging;

import com.eventos.notificacao_service.dto.NotificacaoRequest;
import com.eventos.notificacao_service.service.NotificacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.eventos.notificacao_service.config.RabbitMqConfig.NOTIFICACAO_QUEUE;

@Component
@RequiredArgsConstructor
public class NotificacaoConsumer {

    private final NotificacaoService notificacaoService;

    @RabbitListener(queues = NOTIFICACAO_QUEUE)
    public void consumir(NotificacaoRequest notificacaoRequest) {

        notificacaoService.criar(notificacaoRequest);

        System.out.println("Notificação processada pelo RabbitMq: " + notificacaoRequest);
    }
}
