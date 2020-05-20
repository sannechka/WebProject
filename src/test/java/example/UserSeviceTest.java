package example;


import example.Entity.Room;
import example.Entity.User;
import example.Repository.UserRepo;
import example.Service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserSeviceTest {
    @Autowired
    private UserService userSevice;
    @MockBean
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @MockBean
    private MultipartFile multipartFile;

    @Test
    public void addPhotoFail() throws IOException {
        User user = new User();
        boolean isAddPhoto = userSevice.addPhoto(user, null);
        Assert.assertFalse(isAddPhoto);
        Mockito.verify(userRepo, Mockito.times(0)).save(user);

    }

    @Test
    public void addPhoto() throws IOException {
        User user = new User();
        Mockito.when(multipartFile.getOriginalFilename())
                .thenReturn("file");

        boolean isAddPhoto = userSevice.addPhoto(user, multipartFile);
        Assert.assertTrue(isAddPhoto);
        Mockito.verify(userRepo, Mockito.times(1)).save(user);

    }

    @Test
    public void createUser() {
        User user = new User();
        Room room = new Room();
        user.setPassword("111");
        userSevice.addUser(user, room);
        Mockito.verify(userRepo, Mockito.times(1)).save(user);

    }
}

