package br.com.pegasus.registration.infras.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pegasus.registration.infras.repository.entity.AgencyEntity;

public interface AgencyRepository extends JpaRepository<AgencyEntity, Long> {
}
