package com.asusoftware.easy_booker.appointment.controller;

import com.asusoftware.easy_booker.appointment.model.dto.AppointmentRequestDto;
import com.asusoftware.easy_booker.appointment.model.dto.AppointmentResponseDto;
import com.asusoftware.easy_booker.appointment.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Endpoint pentru a obține toate programările
    @GetMapping
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointments() {
        List<AppointmentResponseDto> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointmentsByUserId(@PathVariable UUID userId) {
        List<AppointmentResponseDto> appointments = appointmentService.getAllAppointmentsByUserId(userId);
        return ResponseEntity.ok(appointments);
    }

    // Endpoint to get upcoming appointments
    @GetMapping("/user/{userId}/upcoming")
    public ResponseEntity<List<AppointmentResponseDto>> getUpcomingAppointments(@PathVariable UUID userId) {
        List<AppointmentResponseDto> appointments = appointmentService.getUpcomingAppointments(userId);
        return ResponseEntity.ok(appointments);
    }

    // Endpoint to get past appointments
    @GetMapping("/user/{userId}/past")
    public ResponseEntity<List<AppointmentResponseDto>> getPastAppointments(@PathVariable UUID userId) {
        List<AppointmentResponseDto> appointments = appointmentService.getPastAppointments(userId);
        return ResponseEntity.ok(appointments);
    }

    // Endpoint to get cancelled appointments
    @GetMapping("/user/{userId}/cancelled")
    public ResponseEntity<List<AppointmentResponseDto>> getCancelledAppointments(@PathVariable UUID userId) {
        List<AppointmentResponseDto> appointments = appointmentService.getCancelledAppointments(userId);
        return ResponseEntity.ok(appointments);
    }

    // Endpoint pentru a șterge o programare după ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable UUID id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}