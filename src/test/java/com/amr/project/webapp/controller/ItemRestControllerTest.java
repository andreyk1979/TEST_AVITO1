package com.amr.project.webapp.controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.model.entity.Item;
import com.amr.project.service.abstracts.ItemService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
public class ItemRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemMapper itemMapper;

    @Test
    public void testStart() throws Exception {
        assertThat(mvc).isNotNull();
    }
    @Test
    public void testFindItemById() throws Exception {
        mvc.perform(get("/api/item/{id}", "1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", Matchers.is(itemService.findById(1L).getName())));
    }
    @Test
    public void testFindAllItem() throws Exception {
        mvc.perform(get("/api/item"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", Matchers.is(itemService.findAll().size())));
    }
    @Test
    public void testAddItem() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/api/item")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"name\": \"test\",\n" +
                        "    \"price\": 333\n}"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Long id = Long.valueOf(mvcResult.getResponse().getContentAsString().split(":")[1].split(",")[0]);
        assertThat("test").isEqualTo(itemService.findById(id).getName());
    }

    @Test
    public void testItemMarkToDeleteShop() throws Exception {
        mvc.perform(delete("/api/item/{id}", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testItemDeleteShop() throws Exception {
        mvc.perform(delete("/api/item/{id}", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
