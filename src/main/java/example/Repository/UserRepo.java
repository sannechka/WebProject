package example.Repository;

import example.Entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MyEntityRepository<User, Long> {

}

