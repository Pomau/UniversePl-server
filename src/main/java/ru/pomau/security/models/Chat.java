package ru.pomau.security.models;

import ru.pomau.security.entity.ChatEntity;
import ru.pomau.security.models.Profile;

import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

public class Chat {
    private String id;
    /*
     * 1 - Чат создан
     * 2 - Первый пользователь отправли приглашение
     * 3 - Второй пользователь принял
     * 4 - Успешно прошла верефикация
     * */
    private int status;
    private Set<Profile> users;
    private Timestamp lastMessage;
    private String p;
    private String g;
    private String publicKeyUser1;
    private String publicKeyUser2;
    private Profile createUser;

    public static Chat toModel(ChatEntity entity) {
        Chat model = new Chat();
        model.setId(entity.getId());
        model.setStatus(entity.getStatus());
        model.setLastMessage(entity.getLastMessage());
        model.setP(entity.getP());
        model.setG(entity.getG());
        model.setPublicKeyUser1(entity.getPublicKeyUser1());
        model.setPublicKeyUser2(entity.getPublicKeyUser2());
        model.setCreateUser(Profile.toModel(entity.getCreateUser()));
        model.setUsers(entity.getUsers().stream().map(Profile::toModel).collect(Collectors.toSet()));
        return model;
    }
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

    public Set<Profile> getUsers() {
        return users;
    }

    public void setUsers(Set<Profile> users) {
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

    public Profile getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Profile createUser) {
        this.createUser = createUser;
    }
}
