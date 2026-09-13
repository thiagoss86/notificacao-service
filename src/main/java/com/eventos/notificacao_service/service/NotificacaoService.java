package com.eventos.notificacao_service.service;

import com.eventos.notificacao_service.dto.NotificacaoRequest;
import com.eventos.notificacao_service.dto.NotificacaoResponse;
import com.eventos.notificacao_service.entity.Notificacao;
import com.eventos.notificacao_service.entity.StatusNotificacao;
import com.eventos.notificacao_service.entity.TipoNotificacao;
import com.eventos.notificacao_service.exception.RecursoNaoEncontradoException;
import com.eventos.notificacao_service.exception.RegraNegocioException;
import com.eventos.notificacao_service.repository.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;

    @Transactional
    public NotificacaoResponse criar(NotificacaoRequest request) {

        Notificacao notificacao = Notificacao.builder()
                .participanteId(request.participanteId())
                .tipoNotificacao(request.tipo())
                .mensagem(request.mensagem())
                .data(LocalDateTime.now())
                .status(StatusNotificacao.PENDENTE)
                .build();

        Notificacao notificacaoSalva = notificacaoRepository.save(notificacao);

        return toResponse(notificacaoSalva);
    }

    @Transactional(readOnly = true)
    public List<NotificacaoResponse> listarTodos() {

        return notificacaoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public NotificacaoResponse buscarPorId(Long id) {

        Notificacao notificacao = buscarEntidadePorId(id);

        return toResponse(notificacao);
    }

    @Transactional(readOnly = true)
    public List<NotificacaoResponse> buscarPorParticipante(Long participanteId) {

        return notificacaoRepository.findByParticipanteId(participanteId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NotificacaoResponse> buscarPorTipo(TipoNotificacao tipo) {

        return notificacaoRepository.findByTipoNotificacao(tipo)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NotificacaoResponse> buscarPorStatus(StatusNotificacao status) {

        return notificacaoRepository.findByStatus(status)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public NotificacaoResponse marcarComoEnviada(Long id) {

        Notificacao notificacao = buscarEntidadePorId(id);

        if (notificacao.getStatus().equals(StatusNotificacao.ENVIADA)) {
            throw new RegraNegocioException("A notificação já foi enviada.");
        }

        notificacao.setStatus(StatusNotificacao.ENVIADA);

        return toResponse(notificacao);
    }

    @Transactional
    public NotificacaoResponse marcarComoFalha(Long id) {

        Notificacao notificacao = buscarEntidadePorId(id);

        if (notificacao.getStatus().equals(StatusNotificacao.ENVIADA)) {
            throw new RegraNegocioException("Não é possível marcar como falha uma notificação já enviada.");
        }

        return toResponse(notificacao);
    }

    private Notificacao buscarEntidadePorId(Long id) {
        return notificacaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Notificação não encontrada."));
    }

    private NotificacaoResponse toResponse(Notificacao notificacaoSalva) {

        return new NotificacaoResponse(
                notificacaoSalva.getId(),
                notificacaoSalva.getParticipanteId(),
                notificacaoSalva.getTipoNotificacao(),
                notificacaoSalva.getMensagem(),
                notificacaoSalva.getData(),
                notificacaoSalva.getStatus()
        );
    }
}
