package br.com.santander.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.santander.registration.repository.entity.AgencyEntity;

public interface AgencyRepository extends JpaRepository<AgencyEntity, Long> {
}
