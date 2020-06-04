package example.Controller;

import example.Entity.Role;
import example.Entity.Room;
import example.Entity.User;
import example.Repository.RoomRepo;
import example.Service.RoomService;
import example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }


    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = ControlerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorMap);
            return "registration";
        }
            if (!userService.addUser(user)) {
                model.addAttribute("usernameError", "User exists");
                System.out.println("hfrelfi");
                return "registration";
            }
            Room roomWithAdmin = roomService.getRoomWithAdmin(user.getUsername(), userService.findByUsername("Admin"));
            userService.addRooms(user, roomWithAdmin, roomService.findByRoomId((long) 1));
        return "redirect:/login";
    }
}
