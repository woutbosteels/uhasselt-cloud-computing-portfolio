package eu.bosteels.wout.kwikbeheer.controller.graphql;

import eu.bosteels.wout.kwikbeheer.graphql.BuildingDAO;
import eu.bosteels.wout.kwikbeheer.graphql.RoomDAO;
import eu.bosteels.wout.kwikbeheer.graphql.TemperatureMeasurementDAO;
import eu.bosteels.wout.kwikbeheer.model.Building;
import eu.bosteels.wout.kwikbeheer.model.Room;
import eu.bosteels.wout.kwikbeheer.model.TemperatureMeasurement;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class RecentReadingController {

    private final TemperatureMeasurementDAO temperatureMeasurementDAO;
    private final RoomDAO roomDAO;
    private final BuildingDAO buildingDAO;

    public RecentReadingController(TemperatureMeasurementDAO temperatureMeasurementDAO, RoomDAO roomDAO, BuildingDAO buildingDAO) {
        this.temperatureMeasurementDAO = temperatureMeasurementDAO;
        this.roomDAO = roomDAO;
        this.buildingDAO = buildingDAO;
    }

    @QueryMapping
    public List<TemperatureMeasurement> recentReadings(@Argument int count, @Argument int offset) {
        return temperatureMeasurementDAO.getRecentTemperatureMeasurements(count, offset);
    }

    @SchemaMapping
    public Room room(TemperatureMeasurement temperatureMeasurement) {
        return roomDAO.getRoom(temperatureMeasurement.getRoom().getId());
    }

    @SchemaMapping
    public Building building(Room room) {
        return buildingDAO.getBuilding(room.getBuilding().getId());
    }


}
