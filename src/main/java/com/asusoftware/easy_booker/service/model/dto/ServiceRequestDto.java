package com.asusoftware.easy_booker.service.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServiceRequestDto {
    private String serviceName;
    private String description;
    private Integer duration; // durata în minute
    private BigDecimal price;
}
