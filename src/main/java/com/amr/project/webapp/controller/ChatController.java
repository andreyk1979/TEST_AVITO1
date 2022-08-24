package com.amr.project.webapp.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.amr.project.facade.ChatFacade;
import com.amr.project.model.dto.MessageDto;
import com.amr.project.model.dto.NotificationDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(description = "Контроллер чата с WebSocket")
public class ChatController {

    private final ChatFacade chatFacade;

    public ChatController(ChatFacade chatFacade) {
        this.chatFacade = chatFacade;
    }

    @ApiOperation(value = "Метод getChatPage",
            notes = "Метод возращает html страницу chatPage")
    @GetMapping("/chat")
    public String getChatPage() {
        return chatFacade.getChatPage();
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public NotificationDto messageProcessing(MessageDto messageDto) {
        return chatFacade.messageProcessing(messageDto);
    }
}
