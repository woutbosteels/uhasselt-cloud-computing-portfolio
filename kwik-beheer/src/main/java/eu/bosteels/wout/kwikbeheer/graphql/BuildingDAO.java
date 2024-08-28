package eu.bosteels.wout.kwikbeheer.graphql;

import eu.bosteels.wout.kwikbeheer.model.Building;
import eu.bosteels.wout.kwikbeheer.repository.BuildingRepository;
import org.springframework.stereotype.Service;

@Service
public class BuildingDAO {

    private final BuildingRepository buildingRepository;

    public BuildingDAO(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public Building getBuilding(long id) {
        return buildingRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}

