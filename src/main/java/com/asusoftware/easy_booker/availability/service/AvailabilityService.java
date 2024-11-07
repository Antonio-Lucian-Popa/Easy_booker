package com.asusoftware.easy_booker.availability.service;

import com.asusoftware.easy_booker.availability.model.Availability;
import com.asusoftware.easy_booker.availability.model.dto.AvailabilityRequestDto;
import com.asusoftware.easy_booker.availability.model.dto.AvailabilityResponseDto;
import com.asusoftware.easy_booker.availability.repository.AvailabilityRepository;
import com.asusoftware.easy_booker.user.model.User;
import com.asusoftware.easy_booker.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {
    
    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private UserRepository userRepository;


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
