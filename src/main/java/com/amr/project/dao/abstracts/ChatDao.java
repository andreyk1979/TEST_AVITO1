package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Chat;
import java.util.Set;

public interface ChatDao extends ReadWriteDao<Chat, Long>{
    boolean existChatByUsers(String senderName, String recipientName);
    Chat getChatByUsers(String senderName, String recipientName);
    Set<Chat> getChatSetByUserName(String userName);
}
