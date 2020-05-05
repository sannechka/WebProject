package example.Controller;

import example.Entity.User;
import example.Repository.UserRepo;
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
    private UserRepo userRepo;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }


    @GetMapping("/addphoto")
    public String photoform(Map<String, Object> model) {
        return "addphoto";
    }


    @PostMapping("/addphoto")
    public String aphoto(@AuthenticationPrincipal
                                  User user,
                          @RequestParam("file") MultipartFile file) throws IOException {
        System.out.println(user);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));
            user.setFilename(resultFileName);
            userRepo.save(user);
        }
        return "addphoto";
    }
}