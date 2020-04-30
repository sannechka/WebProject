package example.Controller;
import example.Entity.Role;
import example.Entity.User;
import example.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")

public class UserController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String userList(Map<String, Object> model) {
        model.put("users", userRepo.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Map<String, Object> model) {
        model.put("user", user);
        model.put("roles", Role.values());
        return "userEdit";
    }


    @PostMapping
    public String userSave(@RequestParam String username,
                           @RequestParam Map<String, String> form,
                           @RequestParam("userId") User user) {
        System.out.println(form);
        user.setUsername(username);
        userRepo.save(user);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            System.out.println("ключ у юзера" + key );
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
                System.out.println("ключ у юзера" + key + " " + Role.valueOf(key));
            }
        }
        userRepo.save(user);
        return "redirect:/user";
    }
}
