package com.asusoftware.easy_booker.notification.model.dto;

import lombok.Data;

@Data
public class NotificationMessageDto {
    private String message;
    private String type; // Tipul notificării, ex. "email", "sms"
}
