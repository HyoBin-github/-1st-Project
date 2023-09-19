package org.project.dev.chatbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chatbot")
public class ChatBotController {

    @GetMapping("/chat")
    public String chatbot(){
        return "chatbot/chat";
    }

}
