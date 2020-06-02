package example.Repository;

import example.Entity.User;

import java.util.List;

public interface MyEntityRepository<T, ID> {
    <S extends T> S saveNewUser(S entity);

    <S extends T> S save(S entity);

    List<T> findAll();

    User findByUsername(String username);

}
