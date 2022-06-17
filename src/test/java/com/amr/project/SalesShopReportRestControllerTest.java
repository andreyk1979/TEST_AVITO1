package com.amr.project;

import com.amr.project.service.abstracts.SalesHistoryService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SalesShopReportRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SalesHistoryService salesHistoryService;

    @Test
    public void shouldReturnAllSales() throws Exception {
        mockMvc.perform(get("/api/sales/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", Matchers.is(salesHistoryService.getListByShopIdFromOrders(1L).size())));
    }

    @Test
    public void shouldReturnAllSalesByItemName() throws Exception {
        mockMvc.perform(get("/api/sales/{id}/{itemName}", "1", "Galaxy S22 Ultra"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sales[0].item").value("Galaxy S22 Ultra"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void shouldReturnSalesByDate() throws Exception {
        mockMvc.perform(get("/api/sales/date/{id}/{date}", "1", "2022-06-16"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.sales[0].item").value("Galaxy S22 Ultra"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void shouldReturnSalesByVariousDates() throws Exception {
        mockMvc.perform(get("/api/sales/filterByDate/{id}/{date1}/{date2}", "1", "2022-06-1", "2022-06-30"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.sales[0].item").value("Galaxy S22 Ultra"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void shouldReturnErrorWrongId() throws Exception {
        mockMvc.perform(get("/api/sales/{id}", "asdasdad"))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andReturn();
    }
}
