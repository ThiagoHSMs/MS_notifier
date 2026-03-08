package com.thiago.dev.ms_notifier.consumer;

import com.thiago.dev.ms_notifier.model.Notification;
import com.thiago.dev.ms_notifier.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    private final NotificationService service;

    public NotificationConsumer(NotificationService service) {
        this.service = service;
    }

    // Este método "escuta" a fila chamada 'notifications.v1'
    @RabbitListener(queues = "notifications.v1")
    public void consume(Notification notification) {
        System.out.println("Mensagem recebida da fila: " + notification.destination());
        service.sendAndSave(notification);
    }
}