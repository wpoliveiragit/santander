package br.com.pegasus.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pegasus.registration.repository.entity.AgencyEntity;

public interface AgencyRepository extends JpaRepository<AgencyEntity, Long> {
}
