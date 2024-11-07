package com.asusoftware.easy_booker.service.controller;

import com.asusoftware.easy_booker.service.model.dto.ServiceRequestDto;
import com.asusoftware.easy_booker.service.model.dto.ServiceResponseDto;
import com.asusoftware.easy_booker.service.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceService serviceService;

    @Autowired
    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponseDto>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> getServiceById(@PathVariable UUID id) {
        return ResponseEntity.ok(serviceService.getServiceById(id));
    }

    @PostMapping
    public ResponseEntity<ServiceResponseDto> createService(@RequestBody ServiceRequestDto ServiceRequestDto) {
        return ResponseEntity.ok(serviceService.createService(ServiceRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> updateService(@PathVariable UUID id, @RequestBody ServiceRequestDto ServiceRequestDto) {
        return ResponseEntity.ok(serviceService.updateService(id, ServiceRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable UUID id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}