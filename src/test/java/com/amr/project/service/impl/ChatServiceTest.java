package com.amr.project.service.impl;

import com.amr.project.service.abstracts.ChatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatServiceTest {

    @Autowired
    private ChatService service;

    @Test
    public void existChatByUsersTest(){
        boolean exist1 = service.existChatByUsers("user", "seller");
        boolean exist2 = service.existChatByUsers("seller", "user");
        boolean exist3 = service.existChatByUsers("user", "user");
        boolean exist4 = service.existChatByUsers("seller", "seller");
        assertTrue(exist1);
        assertTrue(exist2);
        assertFalse(exist3);
        assertFalse(exist4);
    }

    @Test
    public void getChatByUsersTest(){
        assertThat(service.getChatByUsers("user", "seller").getId()).isEqualTo(1L);
    }
    @Test
    public void getChatSetByUserNameTest(){
        assertThat(service.getChatSetByUserName("user").size()).isOne();
        assertThat(service.getChatSetByUserName("admin").size()).isZero();
    }
}
