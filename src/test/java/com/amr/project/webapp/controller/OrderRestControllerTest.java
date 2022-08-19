package com.amr.project.webapp.controller;

import com.amr.project.model.entity.Order;
import com.amr.project.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc()
//@ActiveProfiles("dev")
public class OrderRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderServiceImpl orderService;
    @PersistenceContext
    EntityManager em;


    @Test
    public void testStart()  {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    void shouldCreateOrder() throws Exception {
        int countOrder = orderService.findAll().size();
        mockMvc.perform(put("/api/order")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\n" +
                                "\"date\": \"2017-07-09T11:06:22\",\n" +
                                "\"userId\": \"2\"\n" +
                                "}"))
                .andExpect(status().isOk());
        assertThat(countOrder + 1).isEqualTo(orderService.findAll().size());
    }
}
