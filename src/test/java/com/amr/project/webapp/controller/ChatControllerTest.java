package com.amr.project.webapp.controller;

import com.amr.project.service.abstracts.UserService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc()
public class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService service;

    @Test
    public void getChatPageTest() throws Exception{
        mockMvc.perform(get("/chat"))
                .andExpect(status().isOk())
                .andExpect(view().name("chatPage"))
                .andExpect(content().string(containsString("Chat")))
                .andDo(print());
    }

    @Test
    public void getChatByIdTest() throws Exception {
        mockMvc.perform(get("/chat/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.toUserName", Matchers.is(service.findById(2L).getUsername())))
                .andDo(print());
    }
}
