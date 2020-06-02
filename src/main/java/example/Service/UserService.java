package example.Service;

import example.Entity.Role;
import example.Entity.Room;
import example.Entity.User;
import example.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    FileService fileService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public boolean addUser(User user) {
        Lock lock = new ReentrantLock();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setRoles(Collections.singletonList(Role.USER));
        if (userRepo.findByUsername(user.getUsername()) != null) {
            return false;
        } else {
            lock.lock();
            if (userRepo.findByUsername(user.getUsername()) != null) {
                return false;
            } else {
                userRepo.save(user);
            }
            lock.unlock();
        }

        return true;
    }

    public boolean addPhoto(User user, MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {

            user.setFilename(fileService.filePath(file));
            userRepo.save(user);
            return true;
        }
        return false;
    }

    public List<Room> getRooms(User user) {
        return userRepo.findByUsername(user.getUsername()).getRooms();
    }

    public List<User> getListOfUsers(User user) {
        return userRepo.findAll().stream()
                .filter(x -> !x.isAdmin() && !x.getUsername().equals(user.getUsername()))
                .collect(Collectors.toList());
    }

    public void addRooms(User user, Room roomWithAdmin, Room mainRoom) {
        user.getRooms().add(roomWithAdmin);
        user.getRooms().add(mainRoom);
        userRepo.save(user);
    }
}

