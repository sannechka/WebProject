package example.Controller;

import example.Entity.Message;

import example.Entity.User;
import example.Repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Iterable<Message> messages = messageRepo.findAll().stream().filter(x-> x.getRoom()==null).collect(Collectors.toList());
        model.put("messages", messages);
        return "chat";
    }
    @PostMapping("/chat")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam("file") MultipartFile file,
                      @Valid Message message,
                      BindingResult bindingResult,
                      Model model) throws IOException {
         message.setAuthor(user);
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
        Iterable<Message> messages = messageRepo.findAll().stream().filter(x-> x.getRoom()==null).collect(Collectors.toList());;
        model.addAttribute("messages", messages);
        return "chat";
    }


}

