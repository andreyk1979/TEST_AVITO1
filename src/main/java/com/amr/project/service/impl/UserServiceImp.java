package com.amr.project.service.impl;

import com.amr.project.converter.UserMapper;
import com.amr.project.dao.UserRepository;
import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.Basket;
import com.amr.project.model.entity.User;
import com.amr.project.model.enums.Roles;
import com.amr.project.service.abstracts.MailService;
import com.amr.project.service.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class UserServiceImp extends ReadWriteServiceImpl<User, Long> implements UserService {
    private final UserDao userDao;
    private final UserMapper userMapper;
    private final MailService mailService;
    private PasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    @Autowired
    public void setBCryptPasswordEncoder(PasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Autowired
    public UserServiceImp(UserDao userDao, MailService mailService, UserMapper userMapper, UserRepository userRepository) {
        super(userDao);
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.mailService = mailService;
        this.userRepository = userRepository;
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
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        String message = String.format("Уважамый ,%s! \n" +
                        "Вы успешно зарегистрировались на сайте Avito, \n" +
                        "для окончании регистрации пройдите по ссылке \n" +
                        "http://localhost:8888/registration/activate/%s",
                user.getUsername(), user.getSecret());
        if (userDao.addUser(user)) {
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

    @Override
    public boolean existByUserName(String userName) {
        return userDao.existByUserName(userName);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public void createNewUserAfterOAuthLoginSuccess(String email, String name,
                                                    AuthenticationProvider provider) {
        User user = new User();

        user.setEmail(email);
        user.setUsername(name);
        user.setRole(Roles.USER);
        user.setAuthenticationProvider(provider);
        user.setActivate(true);


        userRepository.save(user);
    }

    @Override
    public void updateUserAfterOAuthLoginSuccess(User user, String name, AuthenticationProvider provider) {
        user.setUsername(name);
        user.setAuthenticationProvider(provider);

        userRepository.save(user);
    }
}
