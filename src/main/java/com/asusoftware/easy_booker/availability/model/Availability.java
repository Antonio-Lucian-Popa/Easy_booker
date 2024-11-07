package com.asusoftware.easy_booker.availability.model;

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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "day_of_week", nullable = false)
    private int dayOfWeek;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;
}
