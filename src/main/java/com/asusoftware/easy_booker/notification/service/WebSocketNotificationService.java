package com.asusoftware.easy_booker.notification.service;

import com.asusoftware.easy_booker.notification.model.dto.NotificationMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Trimite notificarea către toți clienții conectați.
     *
     * @param notificationMessage Mesajul de notificare ce va fi trimis
     */
    public void sendNotification(NotificationMessageDto notificationMessage) {
        // Trimite notificarea către toți clienții conectați la topicul "/topic/notifications"
        messagingTemplate.convertAndSend("/topic/notifications", notificationMessage);
    }
}