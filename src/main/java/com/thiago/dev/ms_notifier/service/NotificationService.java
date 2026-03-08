package com.thiago.dev.ms_notifier.service;

import org.springframework.stereotype.Service;

import com.thiago.dev.ms_notifier.exception.NotificationPersistenceException;
import com.thiago.dev.ms_notifier.model.Notification;
import com.thiago.dev.ms_notifier.repository.NotificationRepository;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository){
        this.repository = repository;
    }

public Notification sendAndSave(Notification notification) {
        if (notification.destination() == null || notification.destination().isBlank()) {
            throw new IllegalArgumentException("Destino inválido");
        }
        
        Notification saved = repository.save(notification);

        
        if (saved == null) {
        throw new NotificationPersistenceException("Erro interno: Não foi possível persistir a notificação.");
        
        }

        return saved;
    }

}
