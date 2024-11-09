package com.asusoftware.easy_booker.appointment.controller;

import com.asusoftware.easy_booker.appointment.model.dto.AppointmentRequestDto;
import com.asusoftware.easy_booker.appointment.model.dto.AppointmentResponseDto;
import com.asusoftware.easy_booker.appointment.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Endpoint pentru a crea o nouă programare
    @PostMapping
    public ResponseEntity<AppointmentResponseDto> createAppointment(@RequestBody AppointmentRequestDto AppointmentRequestDto) {
        AppointmentResponseDto createdAppointment = appointmentService.createAppointment(AppointmentRequestDto);
        return ResponseEntity.ok(createdAppointment);
    }

    // Endpoint pentru a actualiza o programare existentă
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> updateAppointment(
            @PathVariable UUID id, @RequestBody AppointmentRequestDto AppointmentRequestDto) {
        AppointmentResponseDto updatedAppointment = appointmentService.updateAppointment(id, AppointmentRequestDto);
        return ResponseEntity.ok(updatedAppointment);
    }

    // Endpoint pentru a obține o programare după ID
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> getAppointmentById(@PathVariable UUID id) {
        AppointmentResponseDto appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointment);
    }

    @GetMapping
    public ResponseEntity<Page<AppointmentResponseDto>> getAllAppointments(Pageable pageable) {
        Page<AppointmentResponseDto> appointments = appointmentService.getAllAppointments(pageable);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<AppointmentResponseDto>> getAllAppointmentsByUserId(
            @PathVariable UUID userId, Pageable pageable) {
        Page<AppointmentResponseDto> appointments = appointmentService.getAllAppointmentsByUserId(userId, pageable);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/user/{userId}/upcoming")
    public ResponseEntity<Page<AppointmentResponseDto>> getUpcomingAppointments(
            @PathVariable UUID userId, Pageable pageable) {
        Page<AppointmentResponseDto> appointments = appointmentService.getUpcomingAppointments(userId, pageable);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/user/{userId}/past")
    public ResponseEntity<Page<AppointmentResponseDto>> getPastAppointments(
            @PathVariable UUID userId, Pageable pageable) {
        Page<AppointmentResponseDto> appointments = appointmentService.getPastAppointments(userId, pageable);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/user/{userId}/cancelled")
    public ResponseEntity<Page<AppointmentResponseDto>> getCancelledAppointments(
            @PathVariable UUID userId, Pageable pageable) {
        Page<AppointmentResponseDto> appointments = appointmentService.getCancelledAppointments(userId, pageable);
        return ResponseEntity.ok(appointments);
    }
    // Endpoint pentru a șterge o programare după ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable UUID id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}