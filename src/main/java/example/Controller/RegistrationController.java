package example.Controller;

import example.Entity.Role;
import example.Entity.Room;
import example.Entity.User;
import example.Repository.RoomRepo;
import example.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoomRepo roomRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }


    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            Map<String,String> errorMap = ControlerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorMap);
            System.out.println(errorMap);
            return "registration";


        }
        User userFromDB = userRepo.findByUsername(user.getUsername());
        if (userFromDB != null) {
            model.addAttribute("usernameError", "User exists");
            return "registration";

        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRooms().add(roomRepo.findByName("Main Room"));
        Room room = new Room();
        room.setName("Chat with admin (" + user.getUsername() + ")");
        roomRepo.save(room);
        user.getRooms().add(room);
        userRepo.findByUsername("admin").getRooms().add(room);
        userRepo.save(user);

        return "redirect:/login";
    }
}
