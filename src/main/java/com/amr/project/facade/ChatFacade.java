package com.amr.project.facade;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.amr.project.converter.MessageMapper;
import com.amr.project.model.dto.MessageDto;
import com.amr.project.model.dto.NotificationDto;
import com.amr.project.model.entity.Chat;
import com.amr.project.model.entity.Message;
import com.amr.project.service.abstracts.ChatService;
import com.amr.project.service.abstracts.MessageService;
import com.amr.project.service.abstracts.UserService;

@Service
@Transactional
public class ChatFacade {

    private final MessageMapper messageMapper;
    private final MessageService messageService;
    private final UserService userService;
    private final ChatService chatService;

    public ChatFacade(MessageMapper messageMapper,
                      MessageService messageService,
                      UserService userService,
                      ChatService chatService) {
        this.messageMapper = messageMapper;
        this.messageService = messageService;
        this.userService = userService;
        this.chatService = chatService;
    }

    public String getChatPage() {
        return "chatPage";
    }

    public NotificationDto messageProcessing(MessageDto messageDto) {
        System.out.println("----------------------IN CHAT--------------------");
        if (!userService.existByUserName(messageDto.getToUserName())
                || messageDto.getToUserName().equals(messageDto.getFromUserName())) {

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
