package com.asusoftware.easy_booker.availability.model.dto;

import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
public class AvailabilityRequestDto {
    private UUID userId; // TODO: in mod normal ar trebuii sa avem service id aici, pentru ca disponibilitatea este pentru acel serviciu, pentru ca un user poate oferi mai multe servici simultan
    private Integer dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
