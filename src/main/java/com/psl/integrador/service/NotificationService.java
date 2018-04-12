package com.psl.integrador.service;

import com.psl.integrador.model.Topic;
import com.psl.integrador.model.enums.NotificationType;
import org.springframework.stereotype.Service;

@Service
public interface NotificationService {

    void sendNotifications(Topic topic, NotificationType notificationType);
}
