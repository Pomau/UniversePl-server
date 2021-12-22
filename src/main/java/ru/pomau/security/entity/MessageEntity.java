package ru.pomau.security.entity;

import org.hibernate.annotations.GenericGenerator;
import ru.pomau.security.interfaces.ChatsObject;

import javax.persistence.*;

@Entity
@Table(name="messages")
public class MessageEntity implements ChatsObject {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    private String text;

    @ManyToOne
    private ChatEntity chat;
    @ManyToOne
    private ProfileEntity user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ChatEntity getChat() {
        return chat;
    }

    public void setChat(ChatEntity chat) {
        this.chat = chat;
    }

    public ProfileEntity getUser() {
        return user;
    }

    public void setUser(ProfileEntity user) {
        this.user = user;
    }
}