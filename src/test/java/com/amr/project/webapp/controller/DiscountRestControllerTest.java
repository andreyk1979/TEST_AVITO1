package com.amr.project.webapp.controller;

import com.amr.project.service.abstracts.DiscountService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DiscountRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DiscountService discountService;

    @Test
    public void shouldCreateMock() throws Exception {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void allDiscounts() throws Exception {
        mockMvc.perform(get("/api/discount/discount"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", Matchers.is(discountService.findAll().size())));
    }

    @Test
    public void deleteDiscount() throws Exception {
        mockMvc.perform(delete("/api/discount/discount/{id}", "1"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void addDiscount() throws Exception {
        int countDiscount = discountService.findAll().size();
        mockMvc.perform(put("/api/discount/discounts")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\n" +
                                "\"minOrder\": \"15\",\n" +
                                "\"shopId\": \"2\"\n" +
                                "}"))
                .andExpect(status().isOk());
        assertThat(countDiscount + 1).isEqualTo(discountService.findAll().size());
    }
}
