package com.thiago.dev.ms_notifier.repository;

import com.thiago.dev.ms_notifier.model.Notification;

public interface NotificationRepository {
    Notification save(Notification notification);

}
