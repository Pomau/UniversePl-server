package ru.pomau.security.models;

import ru.pomau.security.entity.ChatEntity;
import ru.pomau.security.entity.ProfileEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class Profile {

    private String id;
    private String username;

    public static Profile toModel(ProfileEntity entity) {
        Profile model = new Profile();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        return model;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
