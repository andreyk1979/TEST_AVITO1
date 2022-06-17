package com.amr.project.webapp.controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.abstracts.ShopShowcaseService;
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
class ShopShowcaseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ShopShowcaseService shopShowcaseService;

    @Test
    void shouldShowShopShowcase() throws Exception {
        mockMvc.perform(get("/shop/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("showcase"))
                .andExpect(model().attributeExists("singleShop"))
                .andExpect(model().attributeExists("popularItems"))
                .andExpect(model().attribute("logo", Matchers.notNullValue()))
                .andExpect(model().attribute("singleShop", shopMapper.toDto(shopShowcaseService.getShopForShowcase(1l))))
                .andDo(print());

    }
}