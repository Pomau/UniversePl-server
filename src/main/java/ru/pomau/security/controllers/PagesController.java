package ru.pomau.security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PagesController {

    @GetMapping("/")
    public String login(Model model) {
        System.out.println("Page login");
        return "chat/login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        System.out.println("Page registration");
        return "chat/registration";
    }

    @GetMapping("/message")
    public String message(Model model) {
        System.out.println("Page messager");
        return "chat/messager";
    }

}
