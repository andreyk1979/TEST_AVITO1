package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.MessageDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.dto.NotificationDto;
import com.amr.project.model.entity.Message;
import com.amr.project.service.abstracts.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class MessageServiceImpl extends ReadWriteServiceImpl<Message, Long> implements MessageService {
    private MessageDao messageDao;

    @Autowired
    public MessageServiceImpl(ReadWriteDao<Message, Long> dao, MessageDao messageDao) {
        super(dao);
        this.messageDao = messageDao;
    }

    @Override
    public NotificationDto messageProcessing(Message message) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setChatId(message.getChat().getId());
        notificationDto.setFromUserName(message.getSender().getUsername());
        notificationDto.setToUserName(message.getRecipient().getUsername());
        return notificationDto;
    }

    @Transactional
    @Override
    public void messagesViewed(Long chatId) {
        messageDao.messagesViewed(chatId);
    }
}
