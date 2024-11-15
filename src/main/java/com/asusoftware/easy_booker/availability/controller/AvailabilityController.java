package com.asusoftware.easy_booker.availability.controller;

import com.asusoftware.easy_booker.availability.model.dto.AvailabilityRequestDto;
import com.asusoftware.easy_booker.availability.model.dto.AvailabilityResponseDto;
import com.asusoftware.easy_booker.availability.model.dto.TimeIntervalDto;
import com.asusoftware.easy_booker.availability.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/availabilities")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    // Obține toate disponibilitățile
    @GetMapping
    public ResponseEntity<List<AvailabilityResponseDto>> getAllAvailabilities() {
        return ResponseEntity.ok(availabilityService.getAllAvailabilities());
    }

    // Obține o disponibilitate după ID
    @GetMapping("/{id}")
    public ResponseEntity<AvailabilityResponseDto> getAvailabilityById(@PathVariable UUID id) {
        return ResponseEntity.ok(availabilityService.getAvailabilityById(id));
    }

    // Obține intervalele de timp disponibile pentru un serviciu într-o anumită zi
    @GetMapping("/service/{serviceId}/available-time-slots")
    public ResponseEntity<List<TimeIntervalDto>> getAvailableTimeSlots(
            @PathVariable UUID serviceId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<TimeIntervalDto> availableSlots = availabilityService.calculateAvailableTimeSlots(serviceId, date);
        return ResponseEntity.ok(availableSlots);
    }

    // Creează o nouă disponibilitate
    @PostMapping
    public ResponseEntity<AvailabilityResponseDto> createAvailability(@RequestBody AvailabilityRequestDto availabilityRequestDto) {
        return ResponseEntity.ok(availabilityService.createAvailability(availabilityRequestDto));
    }

    // Actualizează o disponibilitate existentă
    @PutMapping("/{id}")
    public ResponseEntity<AvailabilityResponseDto> updateAvailability(
            @PathVariable UUID id, @RequestBody AvailabilityRequestDto availabilityRequestDto) {
        return ResponseEntity.ok(availabilityService.updateAvailability(id, availabilityRequestDto));
    }

    // Șterge o disponibilitate
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvailability(@PathVariable UUID id) {
        availabilityService.deleteAvailability(id);
        return ResponseEntity.noContent().build();
    }
}
