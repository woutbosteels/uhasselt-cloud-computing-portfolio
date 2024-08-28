package eu.bosteels.wout.kwikbeheer.graphql;

import eu.bosteels.wout.kwikbeheer.model.TemperatureMeasurement;
import eu.bosteels.wout.kwikbeheer.service.TemperatureMeasurementService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemperatureMeasurementDAO {

    private final TemperatureMeasurementService temperatureMeasurementService;

    public TemperatureMeasurementDAO(TemperatureMeasurementService temperatureMeasurementService) {
        this.temperatureMeasurementService = temperatureMeasurementService;
    }


    public List<TemperatureMeasurement> getRecentTemperatureMeasurements(int count, int offset) {
        return temperatureMeasurementService.getRecentTemperatureMeasurements(count, offset);

    }

    public List<TemperatureMeasurement> getRoomsTemperatureMeasurements(long roomId) {
        return temperatureMeasurementService.getAllTemperaturesForRoom(roomId);
    }

}
