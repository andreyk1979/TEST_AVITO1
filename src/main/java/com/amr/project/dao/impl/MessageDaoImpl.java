package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.MessageDao;
import com.amr.project.model.entity.Message;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDaoImpl extends ReadWriteDaoImpl<Message, Long> implements MessageDao {
    @Override
    public void messagesViewed(Long chatId) {
        em.createQuery("UPDATE Message m SET m.viewed = : t WHERE m.chat.id = : id")
                .setParameter("t", true)
                .setParameter("id", chatId)
                .executeUpdate();
    }
}
