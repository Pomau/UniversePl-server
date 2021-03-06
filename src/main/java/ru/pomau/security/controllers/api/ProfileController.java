package ru.pomau.security.controllers.api;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pomau.security.additional.Security;
import ru.pomau.security.entity.ProfileEntity;
import ru.pomau.security.exception.UserAlreadyExistException;
import ru.pomau.security.exception.UserNotFoundException;
import ru.pomau.security.models.Profile;
import ru.pomau.security.service.ProfileService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private Security security;


    @PostMapping("/get")
    public List<Profile> profileSearch(@RequestBody Profile profile) {
        return profileService.findPeople(profile.getUsername());
    }

    @PostMapping("/create")
    public Profile profileSearch(@RequestBody ProfileEntity profileEntity) throws UserAlreadyExistException {
        String hash = security.generatorToken(profileEntity.getTokenAccess());
        profileEntity.setTokenAccess(hash);
        Profile profile = profileService.registration(profileEntity);
        return profile;
    }

    @PostMapping("/session")
    public ResponseEntity<?> sessionCreate(@RequestBody ProfileEntity profileEntity, HttpServletResponse response) throws UserNotFoundException {
        Profile profileModel = profileService.getByPublicKey(profileEntity.getPublicKey());
        ProfileEntity profile = profileService.getEntity(profileModel);
        if (security.verifyToken(profile.getTokenAccess(), profileEntity.getTokenAccess())) {
            String session = security.generateSession();
            profileService.updateSession(profile, session);

            response.addCookie(createCookie("session", session));
            response.setContentType("text/plain");
            System.out.println(session);
            return ResponseEntity.ok().body(HttpStatus.OK);
        } else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    private Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);//создаем объект Cookie,
        cookie.setPath("/");//устанавливаем путь
        cookie.setMaxAge(86400);//здесь устанавливается время жизни куки
        return cookie;
    }
}
