package com.asusoftware.easy_booker.appointment.service;

import com.asusoftware.easy_booker.appointment.model.Appointment;
import com.asusoftware.easy_booker.appointment.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Appointment findById(UUID id) {
        return appointmentRepository.findById(id).orElse(null);
    }
}
