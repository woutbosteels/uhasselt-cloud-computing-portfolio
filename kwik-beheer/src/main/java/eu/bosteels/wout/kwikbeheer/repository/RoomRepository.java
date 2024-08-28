package eu.bosteels.wout.kwikbeheer.repository;

import eu.bosteels.wout.kwikbeheer.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByRoomName(String room);

    List<Room> findAllByBuilding_Id(Long buildingId);
}
