package ru.pomau.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.pomau.security.repo.ChatRepo;
import ru.pomau.security.repo.MessageRepo;
import ru.pomau.security.repo.ProfileRepo;
import ru.pomau.security.repo.TokenRepo;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public abstract class MainService {

    @Autowired
    public ChatRepo chatRepo;
    @Autowired
    public ProfileRepo profileRepo;
    @Autowired
    public MessageRepo messageRepo;
    @Autowired
    public TokenRepo tokenRepo;

//    public mainService(ChatRepo chatRepo, ProfileRepo profileRepo) {
//        this.chatRepo = chatRepo;
//        this.profileRepo = profileRepo;
//    }


}
