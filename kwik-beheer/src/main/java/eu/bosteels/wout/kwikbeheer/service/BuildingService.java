package eu.bosteels.wout.kwikbeheer.service;

import eu.bosteels.wout.kwikbeheer.model.Building;
import eu.bosteels.wout.kwikbeheer.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuildingService {

    @Autowired
    BuildingRepository buildingRepository;

    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }

    public Optional<Building> findBuildingById(long id){
        return buildingRepository.findById(id);
    }

}
