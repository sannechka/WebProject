package example.Service;

import example.Entity.Role;
import example.Entity.Room;
import example.Entity.User;
import example.Repository.RoomRepo;
import example.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(ApplicationArguments args) {
        if (roomRepo.findByName("Main room") == null) {
            Room mainroom = new Room();
            mainroom.setName("Main room");
            roomRepo.save(mainroom);
            User user = new User();
            user.setActive(true);
            user.setUsername("Admin");
            user.setRoles(Collections.singleton(Role.ADMIN));
            user.setPassword(passwordEncoder.encode("123"));
            user.getRooms().add(mainroom);
            userRepo.save(user);
        }
    }
}