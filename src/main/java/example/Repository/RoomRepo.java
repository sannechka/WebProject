package example.Repository;

import example.Entity.Message;
import example.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  RoomRepo extends JpaRepository<Room,Long> {
    Room findByName(String name);
}

