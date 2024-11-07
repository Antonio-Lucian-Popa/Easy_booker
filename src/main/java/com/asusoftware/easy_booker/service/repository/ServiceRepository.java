package com.asusoftware.easy_booker.service.repository;

import com.asusoftware.easy_booker.service.model.EasyService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<EasyService, UUID> {}
