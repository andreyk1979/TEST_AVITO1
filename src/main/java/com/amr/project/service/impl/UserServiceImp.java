package com.amr.project.service.impl;

import com.amr.project.converter.UserMapper;
import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.MailService;
import com.amr.project.service.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class UserServiceImp extends ReadWriteServiceImpl<User,Long> implements UserService {
    private final UserDao userDao;
    private final UserMapper userMapper;
    private final MailService mailService;

    @Autowired
    public UserServiceImp(UserDao userDao,MailService mailService, UserMapper userMapper) {
        super(userDao);
        this.userDao= userDao;
        this.userMapper = userMapper;
        this.mailService = mailService;
    }

    @Override
    @Transactional
    public boolean activate(String secret) {
        return userDao.activateUser(secret);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    @Transactional
    public boolean addUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        String message = String.format("Уважамый ,%s! \n" +
                "Вы успешно зарегистрировались на сайте Avito, \n" +
                "для окончании регистрации пройдите по ссылке \n" +
                "http://localhost:8888/registration/activate/%s",
                user.getUsername(), user.getSecret());
        if(userDao.addUser(user)) {
            mailService.send(user.getEmail(), "Окончание регистрации", message);
            return true;
        }
        return false;
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
