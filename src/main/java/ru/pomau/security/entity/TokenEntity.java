package ru.pomau.security.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import ru.pomau.security.interfaces.ChatsObject;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="tokens")
public class TokenEntity implements ChatsObject {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    private String token;
    @UpdateTimestamp
    private Timestamp createdDate;

    @ManyToOne
    private ProfileEntity user;
    @ManyToOne
    private ChatEntity chat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public ProfileEntity getUser() {
        return user;
    }

    public void setUser(ProfileEntity user) {
        this.user = user;
    }

    public ChatEntity getChat() {
        return chat;
    }

    public void setChat(ChatEntity chat) {
        this.chat = chat;
    }
}
