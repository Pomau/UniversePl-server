package ru.pomau.security.controllers.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pomau.security.additional.Security;
import ru.pomau.security.entity.ChatEntity;
import ru.pomau.security.entity.ProfileEntity;
import ru.pomau.security.exception.UserAlreadyExistException;
import ru.pomau.security.exception.UserNotFoundException;
import ru.pomau.security.models.Chat;
import ru.pomau.security.models.Profile;
import ru.pomau.security.service.ChatService;
import ru.pomau.security.service.ProfileService;

import java.util.List;
import java.util.Map;
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
                                    @RequestParam(value = "page", defaultValue = "1") Integer pages) {
        try {
            Profile user = profileService.getBySession(session);
            return chatService.findAll(user, pages);
        } catch (Exception e){
            System.out.println(session + " - " + pages);
            return null;
        }
    }

    @PostMapping("/nowork/get")
    public List<Chat> findNoWorkChat(@CookieValue("session") String session) {
        try {
            Profile user = profileService.getBySession(session);
            return chatService.findNoWorkChat(user);
        } catch (Exception e){
            System.out.println(session);
            return null;
        }
    }

    @PostMapping("/public_key")
    public ResponseEntity<?> findNoWorkChat(@CookieValue("session") String session,
                                     @RequestBody ObjectNode body) {
        try {
            String key = body.get("key").asText();
            String chatId = body.get("chat").asText();
            Profile user = profileService.getBySession(session);
            chatService.createToken(user, chatId, key);
            return ResponseEntity.ok().body(HttpStatus.OK);
        } catch (Exception e){
            System.out.println(session);
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PostMapping("/create")
    public Chat profileSearch(@CookieValue("session") String session,
                              @RequestBody ChatEntity chatEntity) {
        try {
            Profile user = profileService.getBySession(session);
            Set<ProfileEntity> users = chatEntity.getUsers();
            users.add(profileService.getEntity(user));

            chatEntity.setCreateUser(profileService.getEntity(user));
            chatEntity.setUsers(users);
            chatEntity.setStatus(1);

            chatEntity.setP(security.generateP());
            chatEntity.setG(security.generateG());
            return chatService.create(chatEntity);
        } catch (Exception e){
            System.out.println(session + " - " + chatEntity);
            return null;
        }
    }
}
