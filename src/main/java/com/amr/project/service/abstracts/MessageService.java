package com.amr.project.service.abstracts;

import com.amr.project.model.dto.NotificationDto;
import com.amr.project.model.entity.Message;

public interface MessageService extends ReadWriteService<Message, Long>{
    NotificationDto messageProcessing(Message message);
    void messagesViewed(Long chatId);
}
