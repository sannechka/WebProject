package example;

import example.Entity.Message;
import example.Entity.Room;
import example.Entity.User;
import example.Repository.MessageRepo;
import example.Service.MessageService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private MessageService messsgeSevice;
    @MockBean
    private MessageRepo messageRepo;
    @MockBean
    private MultipartFile multipartFile;
    @Value("${upload.path}")
    private static String uploadPath;


    @Test
    public void addPhotoFail() throws IOException {
        Message message = new Message();
        boolean isAddPhoto = messsgeSevice.addFile(message, null);
        Assert.assertFalse(isAddPhoto);
        Mockito.verify(messageRepo, Mockito.times(0)).save(message);

    }

    @Test
    public void addPhoto() throws IOException {
        Message message = new Message();
        Mockito.when(multipartFile.getOriginalFilename())
                .thenReturn("file");
        boolean isAddPhoto = messsgeSevice.addFile(message, multipartFile);
        Assert.assertTrue(isAddPhoto);
        Mockito.verify(messageRepo, Mockito.times(1)).save(message);
    }

    @Test
    public void createMessage() {
        Message message = new Message();
        User user = new User();
        Room room = new Room();
        messsgeSevice.createMessage(message, user, room);
        Mockito.verify(messageRepo, Mockito.times(1)).save(message);

    }

}
