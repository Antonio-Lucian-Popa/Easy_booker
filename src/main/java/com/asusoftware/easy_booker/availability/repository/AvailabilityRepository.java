package com.asusoftware.easy_booker.availability.repository;

import com.asusoftware.easy_booker.availability.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, UUID> {}
