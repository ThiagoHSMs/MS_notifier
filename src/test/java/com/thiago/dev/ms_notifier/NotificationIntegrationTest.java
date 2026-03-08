package com.thiago.dev.ms_notifier;

import com.thiago.dev.ms_notifier.consumer.NotificationConsumer;
import com.thiago.dev.ms_notifier.model.Notification;
import com.thiago.dev.ms_notifier.repository.NotificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest // Aqui o Spring sobe o contexto real da aplicação
class NotificationIntegrationTest {

    @Autowired
    private NotificationConsumer consumer;

    @MockBean
    private NotificationRepository repository; // Simulamos o banco para não dar erro de conexão

    @Test
    @DisplayName("Deve processar o fluxo completo: Fila -> Consumer -> Service -> Repository")
    void deveProcessarFluxoCompleto() {
        // 1. Criamos a mensagem que "chegaria" da fila
        Notification msg = new Notification("thiago@dev.com", "Mensagem de teste real", "EMAIL");
        
        // 2. Simulamos o comportamento do banco
        when(repository.save(any(Notification.class))).thenReturn(msg);

        // 3. EXECUTAMOS o ponto de entrada do microserviço
        consumer.consume(msg);

        // 4. VERIFICAMOS se o repositório foi chamado (provando que o Service funcionou)
        verify(repository, times(1)).save(any(Notification.class));
        
        System.out.println(" Sucesso: A mensagem passou pelo Consumer e chegou no Repository!");
    }
}