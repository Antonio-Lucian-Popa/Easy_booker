package com.asusoftware.easy_booker.service.service;

import com.asusoftware.easy_booker.service.model.EasyService;
import com.asusoftware.easy_booker.service.model.dto.ServiceRequestDto;
import com.asusoftware.easy_booker.service.model.dto.ServiceResponseDto;
import com.asusoftware.easy_booker.service.repository.ServiceRepository;
import com.asusoftware.easy_booker.user.model.User;
import com.asusoftware.easy_booker.user.model.dto.UserResponseDto;
import com.asusoftware.easy_booker.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ServiceResponseDto> getAllServices() {
        return serviceRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public ServiceResponseDto getServiceById(UUID id) {
        EasyService service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        return convertToResponseDTO(service);
    }

    @Transactional
    public ServiceResponseDto createService(ServiceRequestDto ServiceRequestDto, UUID userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        EasyService service = new EasyService();
        service.setServiceName(ServiceRequestDto.getServiceName());
        service.setDescription(ServiceRequestDto.getDescription());
        service.setDuration(ServiceRequestDto.getDuration());
        service.setPrice(ServiceRequestDto.getPrice());
        service.setUser(user);

        EasyService savedService = serviceRepository.save(service);
        return convertToResponseDTO(savedService);
    }

    @Transactional
    public ServiceResponseDto updateService(UUID id, ServiceRequestDto ServiceRequestDto) {
        EasyService service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        service.setServiceName(ServiceRequestDto.getServiceName());
        service.setDescription(ServiceRequestDto.getDescription());
        service.setDuration(ServiceRequestDto.getDuration());
        service.setPrice(ServiceRequestDto.getPrice());

        EasyService updatedService = serviceRepository.save(service);
        return convertToResponseDTO(updatedService);
    }

    @Transactional
    public void deleteService(UUID id) {
        EasyService service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        serviceRepository.delete(service);
    }

    private ServiceResponseDto convertToResponseDTO(EasyService service) {
        ServiceResponseDto dto = new ServiceResponseDto();
        dto.setId(service.getId());
        dto.setServiceName(service.getServiceName());
        dto.setDescription(service.getDescription());
        dto.setDuration(service.getDuration());
        dto.setPrice(service.getPrice());
       /* UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(service.getUser().getId());
        userResponseDto.setUsername(service.getUser().getUsername());
        userResponseDto.setRole(service.getUser().getRole()); */
        dto.setUserId(service.getUser().getId());
        return dto;
    }
}

