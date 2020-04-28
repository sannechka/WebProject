package example.Controller;

import example.Entity.Message;

import example.Entity.User;
import example.Repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class ChatController {

    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private MessageRepo messageRepo;
    @GetMapping("/")
    public String greeting( Map<String, Object> model) {
        return "greeting";
    }


    @GetMapping("/chat")
    public String main( Map<String, Object> model){
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "chat";
    }
    @PostMapping("/chat")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam("file") MultipartFile file,
                      @RequestParam String text, Map<String, Object> model) throws IOException {
        Message message = new Message(text, user);
        if(file!=null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir= new File(uploadPath);
            if(uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName =uuidFile+"."+ file.getOriginalFilename();
            file.transferTo(new File( uploadPath + "/" +resultFileName));
            message.setFilename(resultFileName);
        }
        messageRepo.save(message);
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "chat";
    }


}

