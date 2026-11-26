package br.com.pegasus.registration.domain.port;

import java.util.Map;

import br.com.pegasus.registration.infras.repository.entity.AgencyEntity;

public interface AgencyServicePort {

  AgencyEntity register(AgencyEntity agency);

  Map<String, Double> calculateDistances(double userX, double userY);
}
