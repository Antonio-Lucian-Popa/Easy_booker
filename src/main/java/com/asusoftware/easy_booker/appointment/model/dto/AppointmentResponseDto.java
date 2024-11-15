package com.asusoftware.easy_booker.appointment.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class AppointmentResponseDto {
    private UUID id;
    private UUID userId;
    private UUID serviceId;
    private LocalDate date;
    private LocalTime time;
    private LocalTime endTime;
    private String description;
    private String status;
    private String userName;
    private String serviceName;
}
