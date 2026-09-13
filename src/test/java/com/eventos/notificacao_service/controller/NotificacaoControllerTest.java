package com.eventos.notificacao_service.controller;

import com.eventos.notificacao_service.dto.NotificacaoRequest;
import com.eventos.notificacao_service.dto.NotificacaoResponse;
import com.eventos.notificacao_service.entity.StatusNotificacao;
import com.eventos.notificacao_service.entity.TipoNotificacao;
import com.eventos.notificacao_service.service.NotificacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificacaoController.class)
public class NotificacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private NotificacaoService notificacaoService;

    @Test
    void deveCriarNotificacaoComSucesso() throws Exception {

        var request = new NotificacaoRequest(
                2L,
                TipoNotificacao.EMAIL,
                "Teste de notificação"
        );

        var response = new NotificacaoResponse(
                1L,
                2L,
                TipoNotificacao.EMAIL,
                "Teste de notificação",
                LocalDateTime.now(),
                StatusNotificacao.PENDENTE
        );

        when(notificacaoService.criar(any(NotificacaoRequest.class)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/notificacoes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.participanteId").value(2))
                .andExpect(jsonPath("$.tipo").value("EMAIL"))
                .andExpect(jsonPath("$.mensagem").value("Teste de notificação"))
                .andExpect(jsonPath("$.status").value("PENDENTE"));
    }

    @Test
    void deveBuscarNotificacaoPorIdComSucesso() throws Exception {

        var response = new NotificacaoResponse(
                1L,
                2L,
                TipoNotificacao.EMAIL,
                "Teste de notificação",
                LocalDateTime.now(),
                StatusNotificacao.PENDENTE
        );

        when(notificacaoService.buscarPorId(1L))
                .thenReturn(response);

        mockMvc.perform(
                        get("/notificacoes/{id}", 1L)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.participanteId").value(2))
                .andExpect(jsonPath("$.tipo").value("EMAIL"))
                .andExpect(jsonPath("$.mensagem").value("Teste de notificação"))
                .andExpect(jsonPath("$.status").value("PENDENTE"));
    }

    @Test
    void deveMarcarNotificacaoComoEnviadaComSucesso() throws Exception {

        var response = new NotificacaoResponse(
                1L,
                2L,
                TipoNotificacao.EMAIL,
                "Teste de notificação",
                LocalDateTime.now(),
                StatusNotificacao.ENVIADA
        );

        when(notificacaoService.marcarComoEnviada(1L))
                .thenReturn(response);

        mockMvc.perform(
                        patch("/notificacoes/{id}/enviada", 1L)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.participanteId").value(2))
                .andExpect(jsonPath("$.tipo").value("EMAIL"))
                .andExpect(jsonPath("$.mensagem").value("Teste de notificação"))
                .andExpect(jsonPath("$.status").value("ENVIADA"));
    }

    @Test
    void deveMarcarNotificacaoComoFalhaComSucesso() throws Exception {

        var response = new NotificacaoResponse(
                1L,
                2L,
                TipoNotificacao.EMAIL,
                "Teste de notificação",
                LocalDateTime.now(),
                StatusNotificacao.FALHA
        );

        when(notificacaoService.marcarComoFalha(1L))
                .thenReturn(response);

        mockMvc.perform(
                        patch("/notificacoes/{id}/falha", 1L)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.participanteId").value(2))
                .andExpect(jsonPath("$.tipo").value("EMAIL"))
                .andExpect(jsonPath("$.mensagem").value("Teste de notificação"))
                .andExpect(jsonPath("$.status").value("FALHA"));
    }
}
