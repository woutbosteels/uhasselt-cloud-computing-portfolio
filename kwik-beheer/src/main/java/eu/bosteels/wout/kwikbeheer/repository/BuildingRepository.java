package eu.bosteels.wout.kwikbeheer.repository;

import eu.bosteels.wout.kwikbeheer.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}
