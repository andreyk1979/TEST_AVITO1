package com.amr.project.webapp.controller;

import com.amr.project.converter.MessageMapper;
import com.amr.project.model.dto.MessageDto;
import com.amr.project.model.dto.NotificationDto;
import com.amr.project.model.entity.Chat;
import com.amr.project.model.entity.Message;
import com.amr.project.service.abstracts.ChatService;
import com.amr.project.service.abstracts.MessageService;
import com.amr.project.service.abstracts.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDateTime;

@Controller
@Api(description = "Контроллер чата с WebSocket")
public class ChatController {

    private MessageMapper messageMapper;
    private MessageService messageService;
    private UserService userService;
    private ChatService chatService;

    @Autowired
    public ChatController(MessageMapper messageMapper, MessageService messageService, UserService userService,
                          ChatService chatService) {
        this.messageMapper = messageMapper;
        this.messageService = messageService;
        this.userService = userService;
        this.chatService = chatService;

    }

    @ApiOperation(value = "Метод getChatPage",
            notes = "Метод возращает html страницу chatPage")
    @GetMapping("/chat")
    public String getChatPage() {
        return "chatPage";
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public NotificationDto messageProcessing(MessageDto messageDto) {
        if (!userService.existByUserName(messageDto.getToUserName())
                ||messageDto.getToUserName().equals(messageDto.getFromUserName())) {

            return new NotificationDto(messageDto.getFromUserName());
        }
            messageDto.setCreationTime(LocalDateTime.now());

        if(!chatService.existChatByUsers(messageDto.getFromUserName(), messageDto.getToUserName())) {
            Chat chat = new Chat();
            chat.setSender(userService.findByUsername(messageDto.getFromUserName()));
            chat.setRecipient(userService.findByUsername(messageDto.getToUserName()));
            chatService.persist(chat);
            messageDto.setChatId(chat.getId());
        }

        if (messageDto.getChatId() == null) {
            messageDto.setChatId(chatService.getChatByUsers(messageDto.getFromUserName(), messageDto.getToUserName())
                    .getId());
        }

        Message message = messageService.persist(messageMapper.toModel(messageDto));
        return messageService.messageProcessing(message);
    }
}
