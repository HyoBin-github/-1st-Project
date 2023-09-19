package org.project.dev.chatbot.controller;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.project.dev.chatbot.message.BotMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public BotMessage chatSt() throws Exception{
        Thread.sleep(50);
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        String formattedDay = today.format(formatter);
        String formattedTime = today.format(DateTimeFormatter.ofPattern("a H:mm"));

        // 처음 실행되는 -> 답장문
        return new BotMessage("");
    }

}
