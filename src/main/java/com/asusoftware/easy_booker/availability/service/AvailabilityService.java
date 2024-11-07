package com.asusoftware.easy_booker.availability.service;

import com.asusoftware.easy_booker.availability.model.Availability;
import com.asusoftware.easy_booker.availability.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AvailabilityService {
    @Autowired
    private AvailabilityRepository availabilityRepository;

    public Availability save(Availability availability) {
        return availabilityRepository.save(availability);
    }

    public List<Availability> findAll() {
        return availabilityRepository.findAll();
    }

    public Availability findById(UUID id) {
        return availabilityRepository.findById(id).orElse(null);
    }
}
