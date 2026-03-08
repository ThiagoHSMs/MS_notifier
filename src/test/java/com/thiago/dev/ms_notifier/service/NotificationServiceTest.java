package com.thiago.dev.ms_notifier.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thiago.dev.ms_notifier.model.Notification;
import com.thiago.dev.ms_notifier.repository.NotificationRepository;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private NotificationRepository repository; // o dublê

    @InjectMocks
    private NotificationService service; // o alvo do teste (recebe dublê)

    @Test
    void deveSalvarNotificacaoComSucesso(){
        //GIVEN
        Notification notificacao = new Notification("thiago@dev.com", "Aula de Mockito", "EMAIL");

        // Ensinamos o Mockito: "Quando o save for chamado com Qualquer notificacao, retorne a nossa""
        when(repository.save(any(Notification.class))).thenReturn(notificacao);

        //WHEN
        Notification resultado = service.sendAndSave(notificacao);

        //THEN
        assertThat(resultado).isNotNull();
        assertThat(resultado.destination()).isEqualTo("thiago@dev.com");

        // VERIFY: Garante que o método save foi chamado exatemente 1 vez
        verify(repository, times(1)).save(notificacao);
    }
    
    @Test
    @DisplayName("Deve lançar exceção quando o repositório retornar null")
    void deveLancarExcecaoQuandoRepositorioRetornarNull() {
        // GIVEN: Configuramos o Mock para retornar null
        Notification notificacao = new Notification("thiago@dev.com", "Conteúdo", "EMAIL");
        when(repository.save(any(Notification.class))).thenReturn(null);

        // WHEN & THEN: O AssertJ captura a exceção e verifica se a mensagem está correta
        assertThatThrownBy(() -> service.sendAndSave(notificacao))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Erro interno: Não foi possível persistir a notificação.");

        // Verificamos se o repositório foi chamado mesmo com o erro
        verify(repository).save(any(Notification.class));
    }
}
