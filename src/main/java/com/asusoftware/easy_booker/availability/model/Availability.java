package com.asusoftware.easy_booker.availability.model;

import com.asusoftware.easy_booker.service.model.EasyService;
import com.asusoftware.easy_booker.user.model.User;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "availability")
public class Availability {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private EasyService service; // Relația cu Service, nu cu User

    @Column(name = "day_of_week", nullable = false)
    private int dayOfWeek;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;
}
