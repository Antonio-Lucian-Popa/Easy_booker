package com.asusoftware.easy_booker.service.service;

import com.asusoftware.easy_booker.service.model.EasyService;
import com.asusoftware.easy_booker.service.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;

    public EasyService save(EasyService service) {
        return serviceRepository.save(service);
    }

    public List<EasyService> findAll() {
        return serviceRepository.findAll();
    }

    public EasyService findById(UUID id) {
        return serviceRepository.findById(id).orElse(null);
    }
}

