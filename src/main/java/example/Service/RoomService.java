package example.Service;

import example.Entity.Message;
import example.Entity.Room;
import example.Entity.User;
import example.Repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepo roomRepo;


    public Room findByRoomId(Long roomId) {
        return roomRepo.findById(roomId).get();
    }


    public Room getRoomWithAdmin(User user, User admin) {
        Room room = new Room();
        room.setName("Chat with admin (" + user.getUsername() + ")");
        room.getUsers().add(admin);
        room.getUsers().add(user);
        roomRepo.save(room);
        return room;
    }

    public void saveRoom(Room room) {
        roomRepo.save(room);
    }

    public List<Message> getMessage(Long roomId) {
        return roomRepo.findById(roomId).get().getMessages();
    }

}
