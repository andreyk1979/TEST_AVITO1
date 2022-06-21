package com.amr.project.webapp.controller;


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
class FavoritePageControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void shouldShowFavoritePage() throws Exception {
        mockMvc.perform(get("/user/2/favorite"))
                .andExpect(status().isOk())
                .andExpect(view().name("favoritePage"))
                .andExpect(model().attributeExists("items"))
                .andExpect(model().attributeExists("shops"))
                .andExpect(model().attribute("items", Matchers.notNullValue()))
                .andExpect(model().attribute("shops", Matchers.notNullValue()))
                .andDo(print());
    }
}
