package com.amr.project.webapp.controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ShopService;
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
class ShopDescriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private ItemMapper itemMapper;

    @Test
    void shouldShowShopShowcase() throws Exception {
        mockMvc.perform(get("/shop/1/description"))
                .andExpect(status().isOk())
                .andExpect(view().name("shopDescription"))
                .andExpect(model().attributeExists("singleShop"))
                .andExpect(model().attributeExists("shopLogo"))
                .andExpect(model().attribute("singleShop", Matchers.notNullValue()))
                .andExpect(model().attribute("singleShop", shopMapper.toDto(shopService.findById(1l))))
                .andDo(print());
    }
}