package ru.pomau.security.controllers.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pomau.security.additional.Security;
import ru.pomau.security.entity.ChatEntity;
import ru.pomau.security.entity.MessageEntity;
import ru.pomau.security.entity.ProfileEntity;
import ru.pomau.security.models.Chat;
import ru.pomau.security.models.Message;
import ru.pomau.security.models.Profile;
import ru.pomau.security.service.ChatService;
import ru.pomau.security.service.MessageService;
import ru.pomau.security.service.ProfileService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/message")
public class MessageController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private Security security;

    @PostMapping("/get")
    public List<Message> findMessage(@CookieValue("session") String session,
                                     @RequestBody Chat chat) {
        try {
            ProfileEntity user = profileService.getEntity(profileService.getBySession(session));
            ChatEntity chatEntity = chatService.getEntity(chat);
            if (chatEntity.getUsers().contains(user)) {
                return messageService.find(chat);
            }
        } catch (Exception e){
            System.out.println(session + " - " + chat);
        }
        return null;
    }

    @PostMapping("/create")
    public ResponseEntity<?> findNoWorkChat(@CookieValue("session") String session,
                                            @RequestBody MessageEntity message) {
        try {
            ProfileEntity user = profileService.getEntity(profileService.getBySession(session));
            ChatEntity chatEntity = chatService.getEntity(chatService.findById(message.getChat().getId()));
            messageService.create(message);
            return ResponseEntity.ok().body(HttpStatus.OK);
        } catch (Exception e){
            System.out.println(session);
        }
        return ResponseEntity.badRequest().body("Error");
    }

}
