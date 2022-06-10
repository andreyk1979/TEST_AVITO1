package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.UserService;
import com.amr.project.webapp.config.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
public class UserServiceImp extends ReadWriteServiceImpl<User, Long> implements UserService {
    private final UserDao userDao;

    private MailConfig mailConfig;

    @Autowired
    public UserServiceImp(UserDao userDao) {
        super(userDao);
        this.userDao = userDao;
    }

    @Autowired
    public void setMailConfig(MailConfig mailConfig) {
        this.mailConfig = mailConfig;
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    @Transactional
    public boolean verifyUserBySecret(User user) {
        user.setActivationCode(String.valueOf(UUID.randomUUID()));
        try {
            MimeMessage message = mailConfig.getMailSender().createMimeMessage();
            message.setSubject("Signing in Avito 2.0");
            message.setText("Your code is - " + user.getActivationCode());
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            mailConfig.getMailSender().send(message);
            dao.update(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
