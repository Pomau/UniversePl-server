package ru.pomau.security.models;


import ru.pomau.security.entity.ChatEntity;
import ru.pomau.security.entity.MessageEntity;
import ru.pomau.security.entity.ProfileEntity;

import java.sql.Timestamp;


public class Message {
    private String id;
    private String text;
    private Profile user;
    private Timestamp createdDate;

    public static Message toModel(MessageEntity entity) {
        Message model = new Message();
        model.setId(entity.getId());
        model.setText(entity.getText());
        model.setUser(Profile.toModel(entity.getUser()));
        model.setCreatedDate(entity.getCreatedDate());
        return model;
    }


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


    public Profile getUser() {
        return user;
    }

    public void setUser(Profile user) {
        this.user = user;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
}
