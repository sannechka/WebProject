package example.Entity;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Message implements Comparable<Message> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Message can't be empty")
    @Length(max = 2048, message = "Message too long")
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_username")
    private User author;
    private String filename;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Long getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public int compareTo(Message o) {
        long a = this.getId();
        long b = o.getId();
        return (a < b) ? -1 : ((a == b) ? 0 : 1);
    }
}
