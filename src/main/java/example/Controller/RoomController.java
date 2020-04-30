package example.Controller;

import example.Entity.Message;
import example.Entity.Room;
import example.Entity.User;
import example.Repository.MessageRepo;
import example.Repository.RoomRepo;
import example.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class RoomController {
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MessageRepo messageRepo;
    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/room")
    public String getRooms(Map<String, Object> model,
                           @AuthenticationPrincipal User user) {
        model.put("rooms", user.getRooms());
        return "room";
    }

    @GetMapping("/createroom")
    public String createRoom(Map<String, Object> model,
                             @AuthenticationPrincipal User user) {
        model.put("users", userRepo.findAll());
        return "createroom";
    }

    @PostMapping("/createroom")
    public String roomSave(@RequestParam String roomname,
                           @RequestParam Map<String, String> form,
                           @AuthenticationPrincipal User user) {
        Room newRoom = new Room();
        newRoom.setName(roomname);
        newRoom.getUsers().add(user);
        List<User> users = userRepo.findAll();
        List<String> names = users.stream().map(User::getUsername).collect(Collectors.toList());
        for (String key : form.keySet()) {
            if (names.contains(key)) {
                userRepo.findByUsername(key).getRooms().add(newRoom);
            }
        }
        user.getRooms().add(newRoom);
        roomRepo.save(newRoom);
        return "redirect:/room";
    }



    @GetMapping("/room/{roomName}")
    public String main(@PathVariable String roomName, Map<String, Object> model) {

        Room room = roomRepo.findByName(roomName);
        Iterable<Message> messages = room.getMessages();
        model.put("messages", messages);
        model.put("room", room);
        return "privatechat";

    }
    @PostMapping("/privatechat")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam("file") MultipartFile file,
                      @RequestParam String roomName,
                      @Valid Message message,
                      BindingResult bindingResult,
                      Model model) throws IOException {
        message.setAuthor(user);
        message.setRoom(roomRepo.findByName(roomName));
        if(bindingResult.hasErrors()){
            bindingResult.getFieldErrors().stream().collect(Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage
            ));
            Map<String,String> errorMap = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage));
            model.mergeAttributes(errorMap);
            model.addAttribute("message", message);

        }
        else {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFileName = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFileName));
                message.setFilename(resultFileName);
            }
            model.addAttribute("message", null);
            messageRepo.save(message);
        }
        Iterable<Message> messages = roomRepo.findByName(roomName).getMessages();
        model.addAttribute("messages", messages);
        model.addAttribute("room",roomRepo.findByName(roomName));
        return "privatechat";
    }
}



