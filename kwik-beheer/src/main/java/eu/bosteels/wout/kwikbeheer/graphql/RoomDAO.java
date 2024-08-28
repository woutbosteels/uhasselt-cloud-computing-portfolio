package eu.bosteels.wout.kwikbeheer.graphql;

import eu.bosteels.wout.kwikbeheer.model.Room;
import eu.bosteels.wout.kwikbeheer.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class RoomDAO {

    private final RoomRepository roomRepository;

    public RoomDAO(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room getRoom(long id) {
        return roomRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}

