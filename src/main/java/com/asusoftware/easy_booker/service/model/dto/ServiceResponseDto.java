package com.asusoftware.easy_booker.service.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ServiceResponseDto {
    private UUID id;
    private UUID userId; // Identifier for the user who created the service
    private String serviceName;
    private String description;
    private Integer duration;
    private BigDecimal price;
}
