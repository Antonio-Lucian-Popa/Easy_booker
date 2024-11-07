package com.asusoftware.easy_booker.notification.controller;

import com.asusoftware.easy_booker.notification.model.dto.NotificationRequestDto;
import com.asusoftware.easy_booker.notification.model.dto.NotificationResponseDto;
import com.asusoftware.easy_booker.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Obține toate notificările
    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> getAllNotifications() {
        List<NotificationResponseDto> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    // Obține o notificare după ID
    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDto> getNotificationById(@PathVariable UUID id) {
        NotificationResponseDto notification = notificationService.getNotificationById(id);
        return ResponseEntity.ok(notification);
    }

    // Creează o nouă notificare
    @PostMapping
    public ResponseEntity<NotificationResponseDto> createNotification(@RequestBody NotificationRequestDto NotificationRequestDto) {
        NotificationResponseDto newNotification = notificationService.createNotification(NotificationRequestDto);
        return ResponseEntity.ok(newNotification);
    }

    // Actualizează statusul unei notificări
    @PutMapping("/{id}/status")
    public ResponseEntity<NotificationResponseDto> updateNotificationStatus(@PathVariable UUID id, @RequestParam("status") String status) {
        NotificationResponseDto updatedNotification = notificationService.updateNotificationStatus(id, status);
        return ResponseEntity.ok(updatedNotification);
    }

    // Șterge o notificare
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable UUID id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
