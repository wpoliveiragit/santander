package com.exercise.registration.repository;

import com.exercise.registration.repository.entity.AgencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyRepository extends JpaRepository<AgencyEntity, Long> {
}
