package com.asusoftware.easy_booker.appointment.service;

import com.asusoftware.easy_booker.appointment.model.Appointment;
import com.asusoftware.easy_booker.appointment.repository.AppointmentRepository;
import com.asusoftware.easy_booker.appointment.model.dto.AppointmentRequestDto;
import com.asusoftware.easy_booker.appointment.model.dto.AppointmentResponseDto;
import com.asusoftware.easy_booker.service.repository.ServiceRepository;
import com.asusoftware.easy_booker.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    // Create a new appointment
    @Transactional
    public AppointmentResponseDto createAppointment(AppointmentRequestDto AppointmentRequestDto) {
        Appointment appointment = new Appointment();
        appointment.setUser(userRepository.findById(AppointmentRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found")));
        appointment.setEasyService(serviceRepository.findById(AppointmentRequestDto.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found")));
        appointment.setDate(AppointmentRequestDto.getDate());
        appointment.setTime(AppointmentRequestDto.getTime());
        appointment.setDescription(AppointmentRequestDto.getDescription());
        appointment.setStatus(AppointmentRequestDto.getStatus());

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return mapToResponseDTO(savedAppointment);
    }

    // Update an existing appointment
    @Transactional
    public AppointmentResponseDto updateAppointment(UUID id, AppointmentRequestDto AppointmentRequestDto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setUser(userRepository.findById(AppointmentRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found")));
        appointment.setEasyService(serviceRepository.findById(AppointmentRequestDto.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found")));
        appointment.setDate(AppointmentRequestDto.getDate());
        appointment.setTime(AppointmentRequestDto.getTime());
        appointment.setDescription(AppointmentRequestDto.getDescription());
        appointment.setStatus(AppointmentRequestDto.getStatus());

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return mapToResponseDTO(updatedAppointment);
    }

    // Get an appointment by ID
    public AppointmentResponseDto getAppointmentById(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        return mapToResponseDTO(appointment);
    }

    // Get all appointments
    public List<AppointmentResponseDto> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // Delete an appointment
    @Transactional
    public void deleteAppointment(UUID id) {
        if (!appointmentRepository.existsById(id)) {
            throw new RuntimeException("Appointment not found");
        }
        appointmentRepository.deleteById(id);
    }

    // Helper method to map Appointment to AppointmentResponseDto
    private AppointmentResponseDto mapToResponseDTO(Appointment appointment) {
        AppointmentResponseDto responseDTO = new AppointmentResponseDto();
        responseDTO.setId(appointment.getId());
        responseDTO.setUserId(appointment.getUser().getId());
        responseDTO.setServiceId(appointment.getEasyService().getId());
        responseDTO.setDate(appointment.getDate());
        responseDTO.setTime(appointment.getTime());
        responseDTO.setDescription(appointment.getDescription());
        responseDTO.setStatus(appointment.getStatus());
        responseDTO.setUserName(appointment.getUser().getUsername());
        responseDTO.setServiceName(appointment.getEasyService().getServiceName());
        return responseDTO;
    }
}
