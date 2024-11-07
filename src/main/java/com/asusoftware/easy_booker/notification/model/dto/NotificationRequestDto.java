package com.asusoftware.easy_booker.notification.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class NotificationRequestDto {
    private UUID appointmentId; // ID-ul programării pentru care se creează notificarea
    private String notificationType; // Tipul notificării, ex. "email" sau "sms"
}
