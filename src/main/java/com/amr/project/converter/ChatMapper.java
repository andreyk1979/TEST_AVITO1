package com.amr.project.converter;

import com.amr.project.model.dto.ChatDto;
import com.amr.project.model.entity.Chat;
import com.amr.project.service.abstracts.UserService;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
@Mapper(componentModel = "spring", uses = {MessageMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ChatMapper {
    @Autowired
    protected UserService userService;

    @Mappings({
            @Mapping(target = "sender", expression = "java(userService.findByUsername(chatDto.getFromUserName()))"),
            @Mapping(target = "recipient", expression = "java(userService.findByUsername(chatDto.getToUserName()))")
    })

    public abstract Chat toModel (ChatDto chatDto);

    @Mappings({
            @Mapping(target = "fromUserName", expression = "java(chat.getSender().getUsername())"),
            @Mapping(target = "toUserName", expression = "java(chat.getRecipient().getUsername())")
    })
    public abstract ChatDto toDto (Chat chat);

    public abstract Set<Chat> toModelSet (Set<ChatDto> DtoSet);
    public abstract Set<ChatDto> toDtoSet (Set<Chat> modelSet);
}
