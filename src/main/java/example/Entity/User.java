package example.Entity;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @NotBlank(message = "Name can't be empty")
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank(message = "Password can't be empty")
    private String password;
    private Boolean active;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_username"))
    @Enumerated(EnumType.STRING)
    private List<Role> roles;
    private String filename;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_rooms",
            joinColumns = {@JoinColumn(name = "user_username")},
            inverseJoinColumns = {@JoinColumn(name = "room_id")}
    )
    private List<Room> rooms = new ArrayList<>();

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Boolean getActive() {
        return active;
    }

    public String getBlock() {
        if (!active) {
            return "Blocked";
        }
        return "";
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User) {
            User usr = (User) obj;
            return usr.username.equals(this.username);
        }
        return false;
    }
}
