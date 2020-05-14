package example.Controller;

import example.Entity.Role;
import example.Entity.User;
import example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user")
    public String userList(Map<String, Object> model) {
        model.put("users", userService.findAll());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("user/{user}")
    public String userEditForm(@PathVariable User user, Map<String, Object> model) {
        model.put("user", user);
        model.put("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/user")
    public String userSave(@RequestParam String username,
                           @RequestParam Map<String, String> form,
                           @RequestParam("userId") User user) {
        user.setUsername(username);
        for (String key : form.keySet()) {
            if (key.equals("block")) {
                user.setActive(false);
            }
            if (key.equals("unblock")) {
                user.setActive(true);
            }
        }
        userService.saveUser(user);
        return "redirect:/user";
    }

    @GetMapping("/addPhoto")
    public String photo() {
        return "addPhoto";
    }


    @PostMapping("/addPhoto")
    public String addPhoto(@AuthenticationPrincipal User user,
                           @RequestParam("file") MultipartFile file) throws IOException {
        userService.addPhoto(user, file);
        return "addPhoto";
    }
}
