package ru.pomau.security.service;

import org.springframework.stereotype.Service;
import ru.pomau.security.entity.MessageEntity;
import ru.pomau.security.entity.TokenEntity;
import ru.pomau.security.exception.UserNotFoundException;
import ru.pomau.security.models.Chat;
import ru.pomau.security.models.Message;
import ru.pomau.security.models.Token;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenService extends MainService{
    public void create(TokenEntity tokenEntity) throws UserNotFoundException {
        if (chatRepo.findById(tokenEntity.getChat().getId()).isEmpty()) {
            throw new UserNotFoundException("Чат не найден");
        }
        tokenRepo.save(tokenEntity);
    }

    public Token find(Chat chat) {
        TokenEntity token = tokenRepo.findByChat_Id(chat.getId());
        return Token.toModel(token);
    }

}