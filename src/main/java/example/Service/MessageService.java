package example.Service;

import example.Entity.Message;
import example.Entity.Room;
import example.Entity.User;
import example.Repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class MessageService {
    @Autowired
    MessageRepo messageRepo;
    @Value("${upload.path}")
    private String uploadPath;

    public void createMessage(Message message, MultipartFile file, User user, Room room) throws IOException {
        message.setAuthor(user);
        message.setTime(new SimpleDateFormat().format(new Date()));
        message.setRoom(room);
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
        messageRepo.save(message);
    }
}
