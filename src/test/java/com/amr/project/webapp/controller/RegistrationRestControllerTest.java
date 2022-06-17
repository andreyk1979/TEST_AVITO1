package com.amr.project.webapp.controller;

import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.MailService;
import com.amr.project.service.abstracts.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class RegistrationRestControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private RegistrationRestController controller;
    @Autowired
    private UserService userService;
    @MockBean
    private MailService mailService;

    @Test
    public void Test() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void addUser() throws Exception {
        this.mvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"username\": \"ivan\",\n" +
                        "\"email\": \"1111@mail.ru\",\n" +
                        "\"password\": \"1\"}"))
                .andDo(print())
                .andExpect(status().isOk());
        User user = userService.findByUsername("ivan");
        verify(mailService, times(1)).send(
                eq("1111@mail.ru"),
                contains("регистрации"),
                contains(user.getSecret())
        );
    }

    @Test
    public void testActivate() throws Exception {
        User user = userService.findByUsername("ivan");
        this.mvc.perform(get("/registration/activate/{code}", user.getSecret()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        User activateUser = userService.findByUsername("ivan");
        assertTrue(activateUser.isActivate());
    }
}