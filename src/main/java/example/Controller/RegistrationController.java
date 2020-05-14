package example.Controller;

import example.Entity.Room;
import example.Entity.User;
import example.Service.RoomService;
import example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

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
        User userFromDB = userService.findByUsername(user.getUsername());
        if (userFromDB != null) {
            model.addAttribute("usernameError", "User exists");
            return "registration";
        }
        Room roomWithAdmin = roomService.getRoomWithAdmin(user.getUsername());
        userService.registrUser(user, roomService.findByRoomName("Main Room"), roomWithAdmin);
        return "redirect:/login";
    }
}
