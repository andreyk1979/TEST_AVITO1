package com.amr.project.webapp.controller;

import com.amr.project.service.abstracts.UserService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserDtoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    public void shouldCreateMock() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void shouldReturnAllUsers() throws Exception {
        mockMvc.perform(get("/api/user/users"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", Matchers.is(userService.findAll().size())));
    }

    @Test
    public void shouldReturnSingleUser() throws Exception {
        mockMvc.perform(get("/api/user/users/{id}", "1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.username", Matchers.is(userService.findById(1L).getUsername())));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/user/user/{id}", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldAddUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/user/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"email\": \"new@mail.com\",\n" +
                                "    \"username\": \"bestUser\",\n" +
                                "    \"phone\": \"new\",\n" +
                                "    \"firstName\": \"first\",\n" +
                                "    \"lastName\": \"last\",\n" +
                                "    \"password\": \"123\",\n" +
                                "    \"age\": \"30\",\n" +
                                "    \"gender\": \"FEMALE\",\n" +
                                "    \"birthday\": null,\n" +
                                "    \"secret\": null,\n" +
                                "    \"image\": null,\n" +
                                "    \"couponIds\": [],\n" +
                                "    \"orderIds\": [],\n" +
                                "    \"reviews\": null,\n" +
                                "    \"feedbacks\": null,\n" +
                                "    \"shopIds\": [],\n" +
                                "    \"favorite\": null,\n" +
                                "    \"discounts\": null,\n" +
                                "    \"chatIds\": [],\n" +
                                "    \"address\": {\n" +
                                "    \"id\": 3,\n" +
                                "    \"cityIndex\": \"142050\",\n" +
                                "    \"street\": \"ул. Белые столбы\",\n" +
                                "    \"house\": \"Большой склад магазина\",\n" +
                                "    \"cityId\": 2,\n" +
                                "    \"city\": \"Москва\",\n" +
                                "    \"countryId\": 1,\n" +
                                "    \"country\": \"Россия\",\n" +
                                "    \"additionalInfo\": null\n" +
                                "    }}"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Long id = Long.valueOf(mvcResult.getResponse().getContentAsString().split(":")[1].split(",")[0]);
        assertThat("bestUser").isEqualTo(userService.findById(id).getUsername());
    }

    //updateUser

}
