package ru.pomau.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.pomau.security.entity.ChatEntity;
import ru.pomau.security.entity.ProfileEntity;
import ru.pomau.security.exception.UserAlreadyExistException;
import ru.pomau.security.exception.UserNotFoundException;
import ru.pomau.security.interfaces.ChatsObject;
import ru.pomau.security.models.Chat;
import ru.pomau.security.models.Profile;
import ru.pomau.security.repo.ChatRepo;
import ru.pomau.security.repo.ProfileRepo;


import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.min;

@Service
public class ChatService extends MainService{

    public Chat create(ChatEntity chatEntity) throws UserAlreadyExistException {
        List<ProfileEntity> a = chatEntity.getUsers().stream().collect(Collectors.toList());
        List<ChatEntity> chats = chatRepo.findByUsers(a.get(0));
        for (ChatEntity chat : chats) {
            if (chat.getUsers().contains(a.get(1))) {
                throw new UserAlreadyExistException("Чат уже существует у пользователей");
            }
        }
        return Chat.toModel(chatRepo.save(chatEntity));
    }

    public List<Chat> findAll(Profile profile, Integer page) throws UserAlreadyExistException {
        ProfileEntity user = profileRepo.findById(profile.getId()).get();
        List<ChatEntity> chats = chatRepo.findByUsersOrderByLastMessageDesc(user, PageRequest.of(page - 1, 100));
//        List<ChatEntity> chatsEntity = (ChatEntity) paginator(chats, page, 100);
        return chats.stream().map(Chat::toModel).collect(Collectors.toList());
    }

    public List<Chat> findNoWorkChat(Profile profile) throws UserAlreadyExistException {
        ProfileEntity user = profileRepo.findById(profile.getId()).get();
        Set<ChatEntity> chats = chatRepo.findByUsersAndStatusLessThan(user, 4);

        for (Iterator<ChatEntity> iterator = chats.iterator(); iterator.hasNext();) {
            ChatEntity chatEntity = (ChatEntity) iterator.next();
            if (!(chatEntity.getCreateUser().getId().equals(user.getId()) && (chatEntity.getStatus() == 1 || chatEntity.getStatus() == 3)
                || !chatEntity.getCreateUser().getId().equals(user.getId()) && chatEntity.getStatus() == 2)) {
                iterator.remove();
            }
        }
        return chats.stream().map(Chat::toModel).collect(Collectors.toList());
    }

    public void createToken(Profile profile, String chatId, String key) throws UserNotFoundException {
        ProfileEntity user = profileRepo.findById(profile.getId()).get();
        ChatEntity chat = chatRepo.findById(chatId).get();
        if (chat.getStatus() == 1 && chat.getCreateUser().getId().equals(user.getId())) {
            chat.setPublicKeyUser2(key);
            chat.setStatus(2);
        } else if (chat.getStatus() == 2 && !chat.getCreateUser().getId().equals(user.getId()) &&
                   chat.getUsers().contains(user)) {
            chat.setPublicKeyUser1(key);
            chat.setStatus(3);
        }
        chatRepo.save(chat);
    }

    public ChatEntity getEntity(Chat chat) throws UserNotFoundException {
        Optional<ChatEntity> chatEntity = chatRepo.findById(chat.getId());
        if (chatEntity == null || chatEntity.isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        return chatEntity.get();
    }

    public Chat findById(String id) throws UserNotFoundException {
        Optional<ChatEntity> chatEntity = chatRepo.findById(id);
        if (chatEntity == null || chatEntity.isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        return Chat.toModel(chatEntity.get());
    }

}
