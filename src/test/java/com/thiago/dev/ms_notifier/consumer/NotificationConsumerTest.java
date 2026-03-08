package com.thiago.dev.ms_notifier.consumer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thiago.dev.ms_notifier.model.Notification;
import com.thiago.dev.ms_notifier.service.NotificationService;

@ExtendWith(MockitoExtension.class)
class NotificationConsumerTest {
    
    @Mock
    private NotificationService service;

    @InjectMocks
    private NotificationConsumer consumer;

    @Test
    @DisplayName("Deve chamar o service corretamente ao consumir uma mensagem")
    void deveChamarServiceAoConsumirMensagem(){
        //GIVEN
        Notification notificacao = new Notification("thiago@dev.com", "Conteudo da Fila", "EMAIL");

        //WHEN
        consumer.consume(notificacao);

        //THEN
        //Verificamos se o metodo SendAndSave do service foi chamado exatamente 1 vez
        // com o objeto que a fila "entregou"
        verify(service, times(1)).sendAndSave(notificacao);

    }
}
