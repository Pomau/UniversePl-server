package ru.pomau.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pomau.security.entity.ProfileEntity;
import ru.pomau.security.exception.UserAlreadyExistException;
import ru.pomau.security.exception.UserNotFoundException;
import ru.pomau.security.models.Profile;
import ru.pomau.security.repo.ProfileRepo;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfileService extends MainService {



    public Profile registration(ProfileEntity user) throws UserAlreadyExistException {
        if (profileRepo.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistException("Пользователь с таким именем существует");
        }
        return toModel(profileRepo.save(user));
    }

    public Profile getBySession(String session) throws UserNotFoundException {
        ProfileEntity user = profileRepo.findBySessionKey(session);
        checkHavePeople(user);
        return toModel(user);
    }

    public Profile getById(String id) throws UserNotFoundException {
        ProfileEntity user = profileRepo.findById(id).orElse(new ProfileEntity());
        checkHavePeople(user);
        return toModel(user);
    }

    public Profile getByPublicKey(String key) throws UserNotFoundException {
        ProfileEntity user = profileRepo.findByPublicKey(key);
        checkHavePeople(user);
        return toModel(user);
    }

    public Profile updateSession(ProfileEntity user, String session) {
        ProfileEntity userEntity = profileRepo.findById(user.getId()).orElse(new ProfileEntity());
        userEntity.setSessionKey(session);
        profileRepo.save(userEntity);
        return toModel(userEntity);
    }

    public List<Profile> findPeople(String username) {
        List<ProfileEntity> userEntity = profileRepo.findAllByUsernameStartsWith(username);
        return userEntity.stream().map(this::toModel).collect(Collectors.toList());
    }

    public ProfileEntity getEntity(Profile profile) throws UserNotFoundException {
        ProfileEntity user = profileRepo.findById(profile.getId()).orElse(new ProfileEntity());
        checkHavePeople(user);
        return user;
    }

    public void checkHavePeople(ProfileEntity profile) throws UserNotFoundException {
        if (profile == null || profile.getId() == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    public Profile toModel(ProfileEntity entity) {
        return Profile.toModel(entity);
    }

}


/*
* */