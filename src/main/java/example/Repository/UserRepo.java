package example.Repository;

import example.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username);

    }

