package example.Service;

import example.Entity.Message;
import example.Entity.Room;
import example.Entity.User;
import example.Repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MessageService {
    @Autowired
    MessageRepo messageRepo;
    @Autowired
    FileService fileService;


    public void createMessage(Message message, User user, Room room) {
        message.setAuthor(user);
        message.setTime(new SimpleDateFormat().format(new Date()));
        message.setRoom(room);
        messageRepo.save(message);
    }

    public boolean addFile(Message message, MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            message.setFilename(fileService.filePath(file));
            messageRepo.save(message);
            return true;
        }
        return false;
    }
}

