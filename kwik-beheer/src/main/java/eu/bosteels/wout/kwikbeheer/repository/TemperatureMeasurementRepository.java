package eu.bosteels.wout.kwikbeheer.repository;

import eu.bosteels.wout.kwikbeheer.model.TemperatureMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemperatureMeasurementRepository extends JpaRepository<TemperatureMeasurement, Long> {

    List<TemperatureMeasurement> findAllByOrderByTsDesc();

    List<TemperatureMeasurement> findAllByRoom_Id(long roomId);
}
