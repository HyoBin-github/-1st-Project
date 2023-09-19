package org.project.dev.chatbot.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.chatbot.message.BotMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chatbot")
public class ChatBotController {

    @GetMapping("/chat")
    public String chatbot(){
        return "chatbot/chat";
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public BotMessage chatSt(){
        return null;
    }

}
