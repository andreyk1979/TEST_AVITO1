package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.MailService;
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

    private MailService mailService;

    @Autowired
    public UserServiceImp(UserDao userDao) {
        super(userDao);
        this.userDao = userDao;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    @Transactional
    public boolean verifyUserBySecret(User user) {
        user.setActivationCode(String.valueOf(UUID.randomUUID()));
        mailService.send(user.getEmail(), "Signing in Avito 2.0", "Your code is - \n" + user.getActivationCode());
        dao.update(user);
        return true;
    }
}
