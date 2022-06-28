package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ChatDao;
import com.amr.project.model.entity.Chat;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class ChatDaoImpl extends ReadWriteDaoImpl<Chat, Long> implements ChatDao {

    @Override
    public boolean existChatByUsers(String senderName, String recipientName) {

        TypedQuery<Chat> query = em.createQuery("SELECT c FROM Chat c WHERE c.sender.username = : s " +
                "AND c.recipient.username = : r OR c.sender.username = : r AND c.recipient.username = : s", Chat.class);

        List<Chat> chatList = query.setParameter("s", senderName)
                .setParameter("r", recipientName).getResultList();

        if (chatList.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public Chat getChatByUsers(String senderName, String recipientName) {
        return em.createQuery("SELECT c FROM Chat c WHERE c.sender.username = : s " +
                "AND c.recipient.username = : r OR c.sender.username = : r AND c.recipient.username = : s",
                        Chat.class)
                .setParameter("s", senderName)
                .setParameter("r", recipientName).getSingleResult();
    }

    @Override
    public Set<Chat> getChatSetByUserName(String userName) {
        List<Chat> chatList = em.createQuery("SELECT c FROM Chat c WHERE c.sender.username = : u " +
                        "OR c.recipient.username = : u", Chat.class)
                .setParameter("u", userName)
                .getResultList();
        return new HashSet<>(chatList);
    }
}
