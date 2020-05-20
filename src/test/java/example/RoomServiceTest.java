package example;

import example.Entity.Room;
import example.Entity.User;
import example.Repository.RoomRepo;
import example.Repository.UserRepo;
import example.Service.RoomService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomServiceTest {

    @Autowired
    private RoomService roomSevice;
    @MockBean
    private RoomRepo roomRepo;
    @MockBean
    private UserRepo userRepo;

    @Test
    public void addRoomWithAdmin() {
        User admin = new User();
        Room room = roomSevice.getRoomWithAdmin("name", admin);
   Assert.assertNotNull(room);
        Mockito.verify(roomRepo, Mockito.times(2)).save(ArgumentMatchers.any());
    }
}

