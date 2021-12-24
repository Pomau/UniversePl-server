package ru.pomau.security.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.pomau.security.entity.ChatEntity;
import ru.pomau.security.entity.ProfileEntity;
import ru.pomau.security.exception.UpdateNotAvailableExistException;
import ru.pomau.security.exception.UserAlreadyExistException;
import ru.pomau.security.exception.UserNotFoundException;
import ru.pomau.security.exception.CantCreateException;
import ru.pomau.security.models.Chat;
import ru.pomau.security.models.Profile;


import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.min;

@Service
public class ChatService extends MainService{

    public Chat create(ChatEntity chatEntity) throws UserAlreadyExistException, CantCreateException {
        List<ProfileEntity> a = new ArrayList<>(chatEntity.getUsers());
        if (a.size() != 2 || a.get(0).equals(a.get(1))) {
            throw new CantCreateException("Чат можно создать только для 2 пользователей");
        }
        List<ChatEntity> chats = chatRepo.findByUsers(a.get(0));
        for (ChatEntity chat : chats) {
            if (chat.getUsers().stream().filter((s) -> s.equals(a.get(1))).count() == 1) {
                throw new UserAlreadyExistException("Чат уже существует у пользователей");
            }
        }
        return Chat.toModel(chatRepo.save(chatEntity));
    }

    public List<Chat> findAll(Profile profile, Integer page) {
        ProfileEntity user = profileRepo.findById(profile.getId()).orElse(new ProfileEntity());
        List<ChatEntity> chats = chatRepo.findByUsersOrderByLastMessageDesc(user, PageRequest.of(page - 1, 100));
        return chats.stream().map(Chat::toModel).collect(Collectors.toList());
    }

    public List<Chat> findNoWorkChat(Profile profile) {
        ProfileEntity user = profileRepo.findById(profile.getId()).orElse(new ProfileEntity());
        Set<ChatEntity> chats = chatRepo.findByUsersAndStatusLessThan(user, 4);

        chats.removeIf(chatEntity ->
                !(chatEntity.getCreateUser().getId().equals(user.getId()) && chatEntity.getStatus() % 2 == 1
                || !chatEntity.getCreateUser().getId().equals(user.getId()) && chatEntity.getStatus() == 2));
        return chats.stream().map(Chat::toModel).collect(Collectors.toList());
    }

    public void createToken(Profile profile, String chatId, String key) throws UpdateNotAvailableExistException {
        ProfileEntity user = profileRepo.findById(profile.getId()).orElse(new ProfileEntity());
        ChatEntity chat = chatRepo.findById(chatId).orElse(new ChatEntity());
        if (chat.getStatus() == 1 && chat.getCreateUser().getId().equals(user.getId())) {
            chat.setPublicKeyUser2(key);
            chat.setStatus(2);
        } else if (chat.getStatus() == 2 && !chat.getCreateUser().getId().equals(user.getId()) &&
                   chat.getUsers().contains(user)) {
            chat.setPublicKeyUser1(key);
            chat.setStatus(3);
        } else {
            throw new UpdateNotAvailableExistException("Обновление токена не доступно");
        }
        chatRepo.save(chat);
    }

    public ChatEntity getEntity(Chat chat) throws UserNotFoundException {
        ChatEntity chatEntity = chatRepo.findById(chat.getId()).orElse(new ChatEntity());
        checkHaveChat(chatEntity);
        return chatEntity;
    }

    public Chat findById(String id) throws UserNotFoundException {
        ChatEntity chatEntity = chatRepo.findById(id).orElse(new ChatEntity());
        checkHaveChat(chatEntity);
        return Chat.toModel(chatEntity);
    }

    public Chat updateStep(ChatEntity chatEntity) {
        return Chat.toModel(chatRepo.save(chatEntity));
    }

    public void checkHaveChat(ChatEntity chat) throws UserNotFoundException {
        if (chat == null || chat.getId() == null) {
            throw new UserNotFoundException("Чат не найден");
        }
    }

}
