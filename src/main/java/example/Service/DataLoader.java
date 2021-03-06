package example.Service;

import example.Entity.Role;
import example.Entity.Room;
import example.Entity.User;
import example.Repository.RoomRepo;
import example.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (roomRepo.findByName("Main room") == null) {
            Room mainroom = new Room();
            mainroom.setName("Main room");
            roomRepo.save(mainroom);
            User user = new User();
            user.setActive(true);
            user.setUsername("Admin");
            user.setRoles(Collections.singletonList(Role.ADMIN));
            user.setPassword(passwordEncoder.encode("123"));
            user.getRooms().add(mainroom);
            userRepo.save(user);
        }
    }
}