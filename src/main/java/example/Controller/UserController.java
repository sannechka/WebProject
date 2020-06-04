package example.Controller;

import example.Entity.User;
import example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user")
    public String userList(Map<String, Object> model) {
        model.put("users", userService.findAll().stream().filter(x -> !x.isAdmin()).collect(Collectors.toList()));
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("user/{user}")
    public String userEditForm(@PathVariable String user, Map<String, Object> model) {
        User userfromBD = userService.findByUsername(user);
        model.put("user", userfromBD);
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/user")
    public String userSave(@RequestParam Map<String, String> form,
                           @RequestParam("userUsername") String user,
                           Model model) {
        User userfromBD = userService.findByUsername(user);
        for (String key : form.keySet()) {
            if (key.equals("block")) {
                userfromBD.setActive(false);
            }
            if (key.equals("unblock")) {
                userfromBD.setActive(true);
            }
        }
        userService.saveUser(userfromBD);
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
