package com.amr.project.converter;

import com.amr.project.model.dto.MessageDto;
import com.amr.project.model.entity.Message;
import com.amr.project.service.abstracts.ChatService;
import com.amr.project.service.abstracts.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class MessageMapper {
    @Autowired
    protected UserService userService;
    @Autowired
    protected ChatService chatService;

    @Mappings({
            @Mapping(target = "creationTime",
                    expression = "java(new java.sql.Timestamp(message.getDate().getTime()).toLocalDateTime())"),
            @Mapping(target = "chatId", expression = "java(message.getChat().getId())"),
            @Mapping(target = "toUserName", expression = "java(message.getRecipient().getUsername())"),
            @Mapping(target = "fromUserName", expression = "java(message.getSender().getUsername())")
    })
    public abstract MessageDto toDto (Message message);

    @Mappings({
            @Mapping(target = "date", expression = "java(java.sql.Timestamp.valueOf(messageDto.getCreationTime()))"),
            @Mapping(target = "chat", expression = "java(chatService.findById(messageDto.getChatId()))"),
            @Mapping(target = "sender", expression = "java(userService.findByUsername(messageDto.getFromUserName()))"),
            @Mapping(target = "recipient", expression = "java(userService.findByUsername(messageDto.getToUserName()))")
    })
    public abstract Message toModel (MessageDto messageDto);

    public abstract List<MessageDto> toDtoList (List<Message> messageList);
    public abstract List<Message> toModelList (List<MessageDto> messageDtoList);
}
