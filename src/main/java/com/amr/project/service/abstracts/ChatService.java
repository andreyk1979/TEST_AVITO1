package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Chat;
import java.util.Set;

public interface ChatService extends ReadWriteService<Chat, Long>{
    boolean existChatByUsers(String senderName, String recipientName);
    Chat getChatByUsers(String senderName, String recipientName);
    Set<Chat> getChatSetByUserName(String userName);
}
