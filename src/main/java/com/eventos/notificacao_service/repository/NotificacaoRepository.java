package com.eventos.notificacao_service.repository;

import com.eventos.notificacao_service.entity.Notificacao;
import com.eventos.notificacao_service.entity.StatusNotificacao;
import com.eventos.notificacao_service.entity.TipoNotificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    List<Notificacao> findByParticipanteId(Long participanteId);

    List<Notificacao> findByStatus(StatusNotificacao status);

    List<Notificacao> findByTipoNotificacao(TipoNotificacao tipo);
}
