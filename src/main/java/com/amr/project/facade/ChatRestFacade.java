package com.amr.project.facade;

import java.security.Principal;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.amr.project.converter.ChatMapper;
import com.amr.project.model.dto.ChatDto;
import com.amr.project.model.entity.Chat;
import com.amr.project.service.abstracts.ChatService;
import com.amr.project.service.abstracts.MessageService;

@Service
@Transactional
public class ChatRestFacade {

    private final MessageService messageService;
    private final ChatService chatService;
    private final ChatMapper chatMapper;

    public ChatRestFacade(MessageService messageService,
                          ChatService chatService,
                          ChatMapper chatMapper) {
        this.messageService = messageService;
        this.chatService = chatService;
        this.chatMapper = chatMapper;
    }

    public Set<ChatDto> getChatSetByUserName(Principal principal) {
        return chatMapper.toDtoSet(chatService.getChatSetByUserName(principal.getName()));
    }

    public ChatDto getChatById(Long id) {
        Chat chat = chatService.findById(id);
        return chatMapper.toDto(chat);
    }

    public ResponseEntity<?> chatMessagesViewed(Long chatId) {
        messageService.messagesViewed(chatId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
