package com.amr.project.webapp.controller;

import com.amr.project.converter.ChatMapper;
import com.amr.project.model.dto.ChatDto;
import com.amr.project.model.entity.Chat;
import com.amr.project.service.abstracts.ChatService;
import com.amr.project.service.abstracts.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Set;

@RestController
@Api(description = "Rest Контроллер чата")
public class ChatRestController {

    private MessageService messageService;
    private ChatService chatService;
    private ChatMapper chatMapper;

    @Autowired
    public ChatRestController(MessageService messageService, ChatService chatService, ChatMapper chatMapper) {
        this.messageService = messageService;
        this.chatService = chatService;
        this.chatMapper = chatMapper;
    }

    @ApiOperation(value = "Метод getChatSetByUserName",
            notes = "Метод принемает Principal возращает список чатов авторизованного юзера (Set<ChatDto>)")
    @GetMapping("/chat/user")
    @ResponseStatus(HttpStatus.OK)
    public Set<ChatDto> getChatSetByUserName(Principal principal) {
        return chatMapper.toDtoSet(chatService.getChatSetByUserName(principal.getName()));
    }

    @ApiOperation(value = "Метод getChatById",
            notes = "Метод принемает id чата возращает ChatDto")
    @GetMapping("/chat/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ChatDto getChatById(@PathVariable("id") Long id) {
        Chat chat = chatService.findById(id);
        return chatMapper.toDto(chat);
    }

    @ApiOperation(value = "Метод getChatById",
            notes = "Метод принемает id чата и обращается к сервису для изменения статуса сообщении данного чата")
    @GetMapping("/chat/messages/{id}")
    public ResponseEntity<?> chatMessagesViewed(@PathVariable("id") Long chatId) {
        messageService.messagesViewed(chatId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
