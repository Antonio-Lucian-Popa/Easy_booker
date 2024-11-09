package com.asusoftware.easy_booker.appointment.repository;

import com.asusoftware.easy_booker.appointment.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    // Metodă personalizată pentru a găsi programările după userId și dată
    List<Appointment> findByUserIdAndDate(UUID userId, LocalDate date);

    Page<Appointment> findAll(Pageable pageable);

    Page<Appointment> findByUserId(UUID userId, Pageable pageable);

    Page<Appointment> findByUserIdAndDateAfter(UUID userId, LocalDate date, Pageable pageable);

    Page<Appointment> findByUserIdAndDateBefore(UUID userId, LocalDate date, Pageable pageable);

    Page<Appointment> findByUserIdAndStatus(UUID userId, String status, Pageable pageable);
}
