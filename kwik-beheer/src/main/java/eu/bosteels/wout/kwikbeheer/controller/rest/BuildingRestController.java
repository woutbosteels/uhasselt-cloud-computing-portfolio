package eu.bosteels.wout.kwikbeheer.controller.rest;

import eu.bosteels.wout.kwikbeheer.model.Building;
import eu.bosteels.wout.kwikbeheer.service.BuildingService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("api/buildings")
public class BuildingRestController {

    @Autowired
    private BuildingService buildingService;

    @GetMapping()
    public List<Building> getAllBuildings() {
        return buildingService.getAllBuildings();
    }

}
