package com.amr.project.config;

import com.amr.project.model.entity.Basket;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class TestConfiguration {
    public static Long idCounter = 1L;
    @Bean(name = "testUserPrototype")
    @Scope("prototype")
    public User user() {
        User user = new User();
        user.setId(idCounter++);
        Basket basket = new Basket(user);
        // fill other User fields if you need them for your tests
        return user;
    }

    @Bean(name = "testItemPrototype")
    @Scope("prototype")
    public Item item() {
        Item item = new Item();
        item.setId(idCounter++);
        return item;
    }
}
