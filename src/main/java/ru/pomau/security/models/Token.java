package ru.pomau.security.models;

import ru.pomau.security.entity.TokenEntity;

import java.sql.Timestamp;

public class Token {
    private String id;
    private String token;
    private Profile user;
    private Chat chat;

    public static Token toModel(TokenEntity entity) {
        Token model = new Token();
        model.setId(entity.getId());
        model.setToken(entity.getToken());
        model.setChat(Chat.toModel(entity.getChat()));
        model.setUser(Profile.toModel(entity.getUser()));
        return model;
    }

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

    public Profile getUser() {
        return user;
    }

    public void setUser(Profile user) {
        this.user = user;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
