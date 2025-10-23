package com.exercise.registration.repository;

import com.exercise.registration.repository.entity.AgencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositório JPA padrão para Agency. */
public interface AgencyRepository extends JpaRepository<AgencyEntity, Long> {
}
