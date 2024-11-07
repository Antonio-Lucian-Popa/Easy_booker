package com.asusoftware.easy_booker.availability.model.dto;

import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
public class AvailabilityRequestDto {
    private UUID userId;
    private Integer dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
