package ru.pomau.security.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pomau.security.additional.Security;
import ru.pomau.security.entity.ChatEntity;
import ru.pomau.security.entity.MessageEntity;
import ru.pomau.security.entity.ProfileEntity;
import ru.pomau.security.entity.TokenEntity;
import ru.pomau.security.exception.UserNotFoundException;
import ru.pomau.security.exception.CantCreateException;
import ru.pomau.security.models.Chat;
import ru.pomau.security.models.Message;
import ru.pomau.security.models.Profile;
import ru.pomau.security.models.Token;
import ru.pomau.security.service.ChatService;
import ru.pomau.security.service.MessageService;
import ru.pomau.security.service.ProfileService;
import ru.pomau.security.service.TokenService;

import java.util.List;

@RestController
@RequestMapping("api/token")
public class TokenController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private Security security;

    @PostMapping("/get")
    public Token findMessage(@CookieValue("session") String session,
                             @RequestBody Chat chat) throws UserNotFoundException {
        ProfileEntity user = profileService.getEntity(profileService.getBySession(session));
        ChatEntity chatEntity = chatService.getEntity(chat);
        if (chatEntity.getUsers().contains(user)) {
            return tokenService.find(chat, Profile.toModel(user));
        }
        return null;
    }

    @PostMapping("/create")
    public ResponseEntity<?> findNoWorkChat(@CookieValue("session") String session,
                                            @RequestBody TokenEntity token) throws UserNotFoundException, CantCreateException {
        ProfileEntity user = profileService.getEntity(profileService.getBySession(session));
        token.setUser(user);
        ChatEntity chatEntity = chatService.getEntity(chatService.findById(token.getChat().getId()));
        if (chatEntity.getStatus() == 3 && chatEntity.getCreateUser().getId().equals(user.getId())) {
            tokenService.create(token);
            chatEntity.setStatus(4);
            chatService.updateStep(chatEntity);
            return ResponseEntity.ok().body(HttpStatus.OK);
        } else if (chatEntity.getStatus() == 2 && !chatEntity.getCreateUser().getId().equals(user.getId())) {
            tokenService.create(token);
            chatService.updateStep(chatEntity);
            return ResponseEntity.ok().body(HttpStatus.OK);
        }else {
            throw new CantCreateException("Вы не можете создать токен");
        }

    }

}
