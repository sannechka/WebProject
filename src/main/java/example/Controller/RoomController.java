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
import sun.util.resources.cldr.CalendarData;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
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
    public String createRoom(Model model,
                             @AuthenticationPrincipal User user) {

        List<User> users = userRepo.findAll().stream().filter(x -> !x.isAdmin() && !x.getUsername().equals(user.getUsername())).collect(Collectors.toList());
        model.addAttribute("users", users);
        return "createroom";
    }

    @PostMapping("/createroom")
    public String roomSave(
            @Valid Room room,
            BindingResult bindingResult,
            Model model,
            @RequestParam Map<String, String> form,
            @AuthenticationPrincipal User user) {

        if (bindingResult.hasErrors()) {
            System.out.println("есть ошибки");
            Map<String, String> errorMap = ControlerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorMap);
            System.out.println(errorMap);
            List<User> users = userRepo.findAll().stream().filter(x -> !x.isAdmin() && !x.getUsername().equals(user.getUsername())).collect(Collectors.toList());
            model.addAttribute("users", users);
            return "createroom";
        } else {

            List<User> users = userRepo.findAll();
            List<String> names = users.stream().map(User::getUsername).collect(Collectors.toList());
            for (String key : form.keySet()) {
                System.out.println(key);
                if (names.contains(key)) {
                    room.getUsers().add(userRepo.findByUsername(key));
                }
                System.out.println( room.getUsers());
                if (room.getUsers().isEmpty()) {
                    model.addAttribute("nameError", "Room must contain at least one user besides you");
                    List<User> users2 = userRepo.findAll().stream().filter(x -> !x.isAdmin() && !x.getUsername().equals(user.getUsername())).collect(Collectors.toList());
                    model.addAttribute("users", users2);
                    return "createroom";
                }
            }
            room.getUsers().add(user);
            roomRepo.save(room);
            return "redirect:/room";
        }

    }

    @GetMapping("/room/{roomName}")
    public String main(@PathVariable String roomName, Map<String, Object> model) {

        Room room = roomRepo.findByName(roomName);
      List<Message> messages = room.getMessages();
      Collections.sort(messages, new Comparator<Message>() {
          @Override
          public int compare(Message o1, Message o2) {
              return o1.getId().compareTo(o2.getId());
          }

      });
      for(Message a:messages){
          System.out.println(a.getId());
      }
        System.out.println();
        model.put("messages", messages);
        model.put("room", room);
        model.put("users", room.getUsers());
        return "privatechat";

    }

    @PostMapping("/privatechat")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam("file") MultipartFile file,
                      @RequestParam String roomName,
                      @Valid Message message,
                      BindingResult bindingResult,
                      Model model) throws IOException, ParseException {
        message.setAuthor(user);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateWithoutTime = sdf.parse(sdf.format(new Date()));
        message.setTime(dateWithoutTime);
        message.setRoom(roomRepo.findByName(roomName));
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().stream().collect(Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage
            ));
            Map<String, String> errorMap = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage));
            model.mergeAttributes(errorMap);
            model.addAttribute("message", message);

        } else {
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
        model.addAttribute("room", roomRepo.findByName(roomName));
        model.addAttribute("users", roomRepo.findByName(roomName).getUsers());
        return "privatechat";
    }
}



