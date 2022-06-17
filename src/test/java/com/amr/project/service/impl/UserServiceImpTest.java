package com.amr.project.service.impl;

import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.User;
import com.amr.project.model.enums.Roles;
import com.amr.project.service.abstracts.MailService;
import com.amr.project.service.abstracts.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImpTest {

    @Autowired
    private UserService userService;

    @MockBean
    private MailService mailService;

    @Test
    public void testActivate() {
        User user = userService.findByUsername("nic");
        assertFalse(user.isActivate());
        assertNotNull(user.getSecret());
        boolean activate = userService.activate(user.getSecret());
        assertTrue(activate);
        User userActivate = userService.findByUsername("nic");
        assertTrue(userActivate.isEnabled());
        assertNull(userActivate.getSecret());
    }

    @Test
    public void addUser() {
        UserDto userDto = new UserDto();
        userDto.setPassword("1");
        userDto.setEmail("11111@mail.ru");
        userDto.setUsername("nic");
        boolean add = userService.addUser(userDto);
        User user = userService.findByUsername("nic");
        assertTrue(add);
        assertNotNull(user.getSecret());
        assertTrue(CoreMatchers.is(user.getRole()).matches(Roles.USER));
        verify(mailService, times(1)).send(
                eq(user.getEmail()),
                contains("регистрации"),
                contains(user.getSecret())
        );
    }

    @Test
    public void findByUsername() {
        assertThat(userService.findByUsername("nic").getUsername()).isEqualTo("nic");
        assertThat(userService.findByUsername("21354ffd")).isEqualTo(null);
    }


    @Test
    public void verifyUserBySecret() {
        User user = new User();
        user.setEmail("0000@.ru");
        user.setUsername("pic");
        boolean activate = userService.verifyUserBySecret(user);
        assertTrue(activate);
        User activateUser = userService.findByUsername("pic");
        assertNotNull(activateUser.getActivationCode());
        verify(mailService, times(1)).send(
                eq(user.getEmail()),
                contains("Avito 2.0"),
                contains(activateUser.getActivationCode())
        );
    }
}