package ru.pomau.security.controllers.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pomau.security.additional.Security;
import ru.pomau.security.entity.ChatEntity;
import ru.pomau.security.entity.ProfileEntity;
import ru.pomau.security.exception.*;
import ru.pomau.security.models.Chat;
import ru.pomau.security.models.Profile;
import ru.pomau.security.service.ChatService;
import ru.pomau.security.service.ProfileService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private Security security;

    @PostMapping("/get")
    public List<Chat> profileSearch(@CookieValue("session") String session,
                                    @RequestParam(value = "page", defaultValue = "1") Integer pages) throws UserNotFoundException {
        Profile user = profileService.getBySession(session);
        return chatService.findAll(user, pages);
    }

    @PostMapping("/nowork/get")
    public List<Chat> findNoWorkChat(@CookieValue("session") String session) throws UserNotFoundException {
        Profile user = profileService.getBySession(session);
        return chatService.findNoWorkChat(user);
    }

    @PostMapping("/public_key")
    public ResponseEntity<?> findNoWorkChat(@CookieValue("session") String session,
                                     @RequestBody ObjectNode body) throws UserNotFoundException, UpdateNotAvailableExistException {
        String key = body.get("key").asText();
        String chatId = chatService.findById(body.get("chat").asText()).getId();
        Profile user = profileService.getBySession(session);
        chatService.createToken(user, chatId, key);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @PostMapping("/create")
    public Chat profileSearch(@CookieValue("session") String session,
                              @RequestBody ChatEntity chatEntity) throws UserNotFoundException, UserAlreadyExistException, CantCreateException {
        Profile user = profileService.getBySession(session);
        Set<ProfileEntity> users = chatEntity.getUsers();
        for (ProfileEntity userEntity: users) {
            profileService.getEntity(profileService.getById(userEntity.getId()));
        }
        users.add(profileService.getEntity(user));


        chatEntity.setCreateUser(profileService.getEntity(user));
        chatEntity.setUsers(users);
        chatEntity.setStatus(1);

        chatEntity.setP(security.generateP());
        chatEntity.setG(security.generateG());
        return chatService.create(chatEntity);
    }
}
