package com.amr.project.webapp.controller;

import com.amr.project.converter.UserMapper;
import com.amr.project.service.abstracts.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc()
class UserPageControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;


    @Test
    void shouldShowUserPage() throws Exception {
        mockMvc.perform(get("/user/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("userPage"))
                .andExpect(model().attributeExists("singleUser"))
                .andExpect(model().attributeExists("avatarka"))
                .andExpect(model().attributeExists("userShop"))
                .andExpect(model().attributeExists("ordersUser"))
                .andExpect(model().attribute("singleUser", Matchers.notNullValue()))
                .andExpect(model().attribute("singleUser", userMapper.toDto(userService.findById(2l))))
                .andDo(print());
    }
}
