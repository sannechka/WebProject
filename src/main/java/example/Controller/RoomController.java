package example.Controller;

import example.Entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class RoomController {

    @GetMapping("/room")
    public String getRooms(Map<String, Object> model,
                           @AuthenticationPrincipal User user) {
        model.put("rooms", user.getRooms());
        return "room";
    }

}
