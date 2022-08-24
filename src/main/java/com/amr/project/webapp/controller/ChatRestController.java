package com.amr.project.webapp.controller;

import java.security.Principal;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.amr.project.facade.ChatRestFacade;
import com.amr.project.model.dto.ChatDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "Rest Контроллер чата")
public class ChatRestController {

    private final ChatRestFacade chatRestFacade;

    public ChatRestController(ChatRestFacade chatRestFacade) {
        this.chatRestFacade = chatRestFacade;
    }

    @ApiOperation(value = "Метод getChatSetByUserName",
            notes = "Метод принемает Principal возращает список чатов авторизованного юзера (Set<ChatDto>)")
    @GetMapping("/chat/user")
    @ResponseStatus(HttpStatus.OK)
    public Set<ChatDto> getChatSetByUserName(Principal principal) {
        return chatRestFacade.getChatSetByUserName(principal);
    }

    @ApiOperation(value = "Метод getChatById",
            notes = "Метод принемает id чата возращает ChatDto")
    @GetMapping("/chat/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ChatDto getChatById(@PathVariable("id") Long id) {
        return chatRestFacade.getChatById(id);
    }

    @ApiOperation(value = "Метод getChatById",
            notes = "Метод принемает id чата и обращается к сервису для изменения статуса сообщении данного чата")
    @GetMapping("/chat/messages/{id}")
    public ResponseEntity<?> chatMessagesViewed(@PathVariable("id") Long chatId) {
        return chatRestFacade.chatMessagesViewed(chatId);
    }
}
