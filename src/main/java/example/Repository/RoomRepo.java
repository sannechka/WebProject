package example.Repository;

import example.Entity.Message;
import example.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepo {
    public interface  MessageRepo extends JpaRepository<Room,Long> {
    }

}
