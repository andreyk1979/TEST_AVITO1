package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Message;

public interface MessageDao extends ReadWriteDao<Message, Long>{
    void messagesViewed(Long chatId);
}
