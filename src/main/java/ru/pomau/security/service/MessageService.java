package ru.pomau.security.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.pomau.security.entity.ChatEntity;
import ru.pomau.security.entity.MessageEntity;
import ru.pomau.security.entity.ProfileEntity;
import ru.pomau.security.exception.UserAlreadyExistException;
import ru.pomau.security.exception.UserNotFoundException;
import ru.pomau.security.models.Chat;
import ru.pomau.security.models.Message;
import ru.pomau.security.models.Profile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Math.min;

@Service
public class MessageService extends MainService{



    public void create(MessageEntity messageEntity) throws UserNotFoundException {
        if (chatRepo.findById(messageEntity.getChat().getId()).isEmpty()) {
            throw new UserNotFoundException("Чат не найден");
        }
        messageRepo.save(messageEntity);
    }

    public List<Message> find(Chat chat) {
        List<MessageEntity> messages = messageRepo.findByChat_IdOrderByCreatedDateAsc(chat.getId());
        return messages.stream().map(Message::toModel).collect(Collectors.toList());
    }

}
