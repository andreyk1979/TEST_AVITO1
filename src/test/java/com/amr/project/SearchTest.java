package com.amr.project;

import com.amr.project.webapp.controller.SearchAvitoRestContoller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
@Sql(value = {"/create-test-search.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete-test-search.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class SearchTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private SearchAvitoRestContoller search;
    @Test
    public void testStart() throws Exception {
       assertThat(search).isNotNull();
    }
    @Test
    public void testFindItem() throws Exception {
        this.mvc.perform(get("/api/search/{string}", "oS"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.itemDtoList[0].name").value("dos"));
    }
    @Test
    public void testFindShop() throws Exception {
        this.mvc.perform(get("/api/search/{string}", "EE"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.shopDtoList[0].name").value("three"));
    }
    @Test
    public void testNoContent() throws Exception {
        this.mvc.perform(get("/api/search/{string}", "fdgfdgdfgfdg"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
