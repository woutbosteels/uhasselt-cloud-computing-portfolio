package eu.bosteels.wout.kwikbeheer.controller.rest;

import eu.bosteels.wout.kwikbeheer.model.Room;
import eu.bosteels.wout.kwikbeheer.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/buildings")
public class RoomRestController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/{buildingId}/rooms")
    public List<Room> getRoomsInBuilding(@PathVariable("buildingId") Long id) {
        return roomService.getRoomsInBuilding(id);
    }

}
