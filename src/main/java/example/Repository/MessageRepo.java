package example.Repository;

import example.Entity.Message;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  MessageRepo extends JpaRepository<Message,Long> {
    Message findByText(String text);
}
