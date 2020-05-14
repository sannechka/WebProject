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

    public Room findByRoomName(String roomName) {
        return roomRepo.findByName(roomName);
    }

    public Room getRoomWithAdmin(String username) {
        Room room = new Room();
        room.setName("Chat with admin (" + username + ")");
        roomRepo.save(room);
        return room;
    }

    public void roomAddUser(Room room, User user) {
        room.getUsers().add(user);
        roomRepo.save(room);
    }

    public List<Message> getMessage(String roomName) {
        return roomRepo.findByName(roomName).getMessages();
    }

}
