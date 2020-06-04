package example.Repository;

import example.Entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional(readOnly = true)
public class MyEntityRepositoryImp implements MyEntityRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public boolean save(User user) {
        try {
            em.persist(user);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
