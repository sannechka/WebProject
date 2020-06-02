package example.Controller;

import example.Entity.Message;
import example.Entity.Room;
import example.Entity.User;
import example.Service.MessageService;
import example.Service.RoomService;
import example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;


    @GetMapping("/room")
    public String getRooms(Map<String, Object> model, @AuthenticationPrincipal User user) {

        model.put("rooms", userService.getRooms(user));
        return "room";
    }

    @GetMapping("/createRoom")
    public String createRoom(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("users", userService.getListOfUsers(user));
        return "createRoom";
    }

    @PostMapping("/createRoom")
    public String roomSave(
            @Valid Room room,
            BindingResult bindingResult,
            Model model,
            @RequestParam Map<String, String> form,
            @AuthenticationPrincipal User user) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = ControlerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorMap);
            model.addAttribute("users", userService.getListOfUsers(user));
            return "createRoom";
        } else {
            List<User> users = userService.findAll();
            List<String> names = users.stream().map(User::getUsername).collect(Collectors.toList());
            for (String key : form.keySet()) {
                if (names.contains(key)) {
                    room.getUsers().add(userService.findByUsername(key));
                }
            }
            if (room.getUsers().isEmpty()) {
                model.addAttribute("nameError", "Room must contain at least one user besides you");
                model.addAttribute("users", userService.getListOfUsers(user));
                return "createRoom";
            }
            room.getUsers().add(user);
            roomService.saveRoom(room);
            return "redirect:/room";
        }

    }

    @GetMapping("/room/{roomId}")
    public String main(@PathVariable Long roomId, Map<String, Object> model) {
        Room room = roomService.findByRoomId(roomId);
        List<Message> messages = roomService.getMessage(roomId);
        Collections.sort(messages);
        model.put("messages", messages);
        model.put("room", room);
        model.put("users", room.getUsers());
        return "privateChat";

    }

    @PostMapping("/privateChat")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam("file") MultipartFile file,
                      @RequestParam Long roomId,
                      @Valid Message message,
                      BindingResult bindingResult,
                      Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = ControlerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorMap);
            model.addAttribute("message", message);
        } else {
            model.addAttribute("message", null);
            messageService.createMessage(message, user, roomService.findByRoomId(roomId));
            messageService.addFile(message, file);
        }
        model.addAttribute("messages", roomService.getMessage(roomId));
        model.addAttribute("room", roomService.findByRoomId(roomId));
        model.addAttribute("users", roomService.findByRoomId(roomId).getUsers());
        return "privateChat";
    }
}
