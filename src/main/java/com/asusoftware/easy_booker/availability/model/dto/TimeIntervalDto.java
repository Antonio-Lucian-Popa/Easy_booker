package com.asusoftware.easy_booker.availability.model.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class TimeIntervalDto {
    private LocalTime startTime;
    private LocalTime endTime;

    public static TimeIntervalDto toDto(LocalTime startTime, LocalTime endTime) {
        TimeIntervalDto timeIntervalDto = new TimeIntervalDto();
        timeIntervalDto.setStartTime(startTime);
        timeIntervalDto.setEndTime(endTime);
        return timeIntervalDto;
    }
}
