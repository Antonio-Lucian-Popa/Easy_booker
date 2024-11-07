package com.asusoftware.easy_booker.service.model;

import lombok.Data;
import jakarta.persistence.*;
import java.util.UUID;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "services")
public class EasyService {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "service_name", nullable = false)
    private String serviceName;

    private String description;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private BigDecimal price;
}