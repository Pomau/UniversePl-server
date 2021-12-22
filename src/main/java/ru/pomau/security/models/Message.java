package ru.pomau.security.models;


import ru.pomau.security.entity.ChatEntity;
import ru.pomau.security.entity.MessageEntity;
import ru.pomau.security.entity.ProfileEntity;



public class Message {
    private String id;
    private String text;
    private Chat chat;
    private Profile user;

    public static Message toModel(MessageEntity entity) {
        Message model = new Message();
        model.setId(entity.getId());
        model.setText(entity.getText());
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Profile getUser() {
        return user;
    }

    public void setUser(Profile user) {
        this.user = user;
    }
}
