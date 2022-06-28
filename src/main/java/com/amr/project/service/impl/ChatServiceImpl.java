package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ChatDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Chat;
import com.amr.project.service.abstracts.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class ChatServiceImpl extends ReadWriteServiceImpl<Chat, Long> implements ChatService {

    private ChatDao chatDao;

    @Autowired
    public ChatServiceImpl(ReadWriteDao<Chat, Long> dao, ChatDao chatDao) {
        super(dao);
        this.chatDao = chatDao;
    }

    @Override
    public boolean existChatByUsers(String senderName, String recipientName) {
        return chatDao.existChatByUsers(senderName, recipientName);
    }

    @Override
    public Chat getChatByUsers(String senderName, String recipientName) {
        return chatDao.getChatByUsers(senderName, recipientName);
    }

    @Override
    public Set<Chat> getChatSetByUserName(String userName) {
        return chatDao.getChatSetByUserName(userName);
    }
}
