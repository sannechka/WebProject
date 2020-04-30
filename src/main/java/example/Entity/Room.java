package example.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "room",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Message> messages;

    public Set<Message> getMessages() {
        return messages;
    }

    public Long getId() {
        return id;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_rooms",
            joinColumns={@JoinColumn( name ="room_id")},
            inverseJoinColumns = {@JoinColumn(name="user_id")}
    )
    private Set<User> users = new HashSet<>();

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Room() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
