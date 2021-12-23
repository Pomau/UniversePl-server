package ru.pomau.security.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Table(name="chats")
public class ChatEntity {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    /*
     * 1 - Чат создан
     * 2 - Первый пользователь отправли приглашение
     * 3 - Второй пользователь принял
     * 4 - Успешно прошла верефикация
     * */
    private int status;

    @ManyToMany
    private Set<ProfileEntity> users;
    private Timestamp lastMessage;
    private String p;
    private String g;
    private String publicKeyUser1;
    private String publicKeyUser2;

    @ManyToOne
    private ProfileEntity createUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<ProfileEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<ProfileEntity> users) {
        this.users = users;
    }

    public Timestamp getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Timestamp lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

    public String getPublicKeyUser1() {
        return publicKeyUser1;
    }

    public void setPublicKeyUser1(String publicKeyUser1) {
        this.publicKeyUser1 = publicKeyUser1;
    }

    public String getPublicKeyUser2() {
        return publicKeyUser2;
    }

    public void setPublicKeyUser2(String publicKeyUser2) {
        this.publicKeyUser2 = publicKeyUser2;
    }

    public ProfileEntity getCreateUser() {
        return createUser;
    }

    public void setCreateUser(ProfileEntity createUser) {
        this.createUser = createUser;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", users=" + users +
                ", lastMessage=" + lastMessage +
                ", p='" + p + '\'' +
                ", g='" + g + '\'' +
                ", publicKeyUser1='" + publicKeyUser1 + '\'' +
                ", publicKeyUser2='" + publicKeyUser2 + '\'' +
                ", createUser=" + createUser +
                '}';
    }
}
