package com.asusoftware.easy_booker.notification.model.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class NotificationResponseDto {
    private UUID id;
    private UUID appointmentId;
    private String notificationType; // Tipul notificării, ex. "email" sau "sms"
    private LocalDateTime sentAt; // Data și ora trimiterii notificării
    private String status; // Statusul notificării, ex. "pending", "sent"
}
