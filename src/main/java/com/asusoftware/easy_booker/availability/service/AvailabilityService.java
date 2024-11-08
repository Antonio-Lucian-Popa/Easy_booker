package com.asusoftware.easy_booker.availability.service;

import com.asusoftware.easy_booker.appointment.model.Appointment;
import com.asusoftware.easy_booker.appointment.repository.AppointmentRepository;
import com.asusoftware.easy_booker.availability.model.Availability;
import com.asusoftware.easy_booker.availability.model.dto.AvailabilityRequestDto;
import com.asusoftware.easy_booker.availability.model.dto.AvailabilityResponseDto;
import com.asusoftware.easy_booker.availability.model.dto.TimeIntervalDto;
import com.asusoftware.easy_booker.availability.repository.AvailabilityRepository;
import com.asusoftware.easy_booker.user.model.User;
import com.asusoftware.easy_booker.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {
    
    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;


    public List<AvailabilityResponseDto> getAllAvailabilities() {
        return availabilityRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public AvailabilityResponseDto getAvailabilityById(UUID id) {
        Availability availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Availability not found"));
        return convertToResponseDTO(availability);
    }

    @Transactional
    public AvailabilityResponseDto createAvailability(AvailabilityRequestDto AvailabilityRequestDto) {
        User user = userRepository.findById(AvailabilityRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Availability availability = new Availability();
        availability.setUser(user);
        availability.setDayOfWeek(AvailabilityRequestDto.getDayOfWeek());
        availability.setStartTime(AvailabilityRequestDto.getStartTime());
        availability.setEndTime(AvailabilityRequestDto.getEndTime());

        Availability savedAvailability = availabilityRepository.save(availability);
        return convertToResponseDTO(savedAvailability);
    }

    @Transactional
    public AvailabilityResponseDto updateAvailability(UUID id, AvailabilityRequestDto AvailabilityRequestDto) {
        Availability availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Availability not found"));

        User user = userRepository.findById(AvailabilityRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        availability.setUser(user);
        availability.setDayOfWeek(AvailabilityRequestDto.getDayOfWeek());
        availability.setStartTime(AvailabilityRequestDto.getStartTime());
        availability.setEndTime(AvailabilityRequestDto.getEndTime());

        Availability updatedAvailability = availabilityRepository.save(availability);
        return convertToResponseDTO(updatedAvailability);
    }

    @Transactional
    public void deleteAvailability(UUID id) {
        if (!availabilityRepository.existsById(id)) {
            throw new RuntimeException("Availability not found");
        }
        availabilityRepository.deleteById(id);
    }

    public List<TimeIntervalDto> calculateAvailableTimeSlots(UUID userId, LocalDate date) {
        int dayOfWeek = date.getDayOfWeek().getValue();

        // Obține intervalul de disponibilitate generală pentru ziua respectivă
        Availability availability = availabilityRepository.findByUserIdAndDayOfWeek(userId, dayOfWeek)
                .orElseThrow(() -> new EntityNotFoundException("No availability found for this day"));

        // Obține programările existente pentru utilizator în acea zi
        List<Appointment> appointments = appointmentRepository.findByUserIdAndDate(userId, date);

        // Calculează intervalele disponibile pe baza disponibilității și a programărilor
        return calculateAvailableSlots(availability, appointments);
    }

    private List<TimeIntervalDto> calculateAvailableSlots(Availability availability, List<Appointment> appointments) {
        List<TimeIntervalDto> availableSlots = new ArrayList<>();
        availableSlots.add(TimeIntervalDto.toDto(availability.getStartTime(), availability.getEndTime()));

        for (Appointment appointment : appointments) {
            availableSlots = splitAvailableSlots(availableSlots, appointment);
        }

        return availableSlots;
    }

    private List<TimeIntervalDto> splitAvailableSlots(List<TimeIntervalDto> availableSlots, Appointment appointment) {
        List<TimeIntervalDto> updatedSlots = new ArrayList<>();

        for (TimeIntervalDto slot : availableSlots) {
            if (appointment.getTime().isBefore(slot.getEndTime()) && appointment.getEndTime().isAfter(slot.getStartTime())) {
                if (appointment.getTime().isAfter(slot.getStartTime())) {
                    updatedSlots.add(TimeIntervalDto.toDto(slot.getStartTime(), appointment.getTime()));
                }
                if (appointment.getEndTime().isBefore(slot.getEndTime())) {
                    updatedSlots.add(TimeIntervalDto.toDto(appointment.getEndTime(), slot.getEndTime()));
                }
            } else {
                updatedSlots.add(slot);
            }
        }

        return updatedSlots;
    }

    private AvailabilityResponseDto convertToResponseDTO(Availability availability) {
        AvailabilityResponseDto dto = new AvailabilityResponseDto();
        dto.setId(availability.getId());
        dto.setUserId(availability.getUser().getId());
        dto.setDayOfWeek(availability.getDayOfWeek());
        dto.setStartTime(availability.getStartTime());
        dto.setEndTime(availability.getEndTime());
        return dto;
    }
}
