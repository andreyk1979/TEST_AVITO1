package com.amr.project;

import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ShopService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc()
class ShopRegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ShopService shopService;

    @Test
    void shouldShowShopRegistrationPage() throws Exception {
        mockMvc.perform(get("/shop/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registrationPage"))
                .andExpect(model().attributeExists("shopToRegister"))
                .andDo(print());
    }

    @Test
    void add() throws Exception {

        mockMvc.perform(post("/shop/registration/create")
                        .param("name", "***")
                        .param("description", "***")
                        .param("email", "***")
                        .param("phone", "***")
                        .param("rating", "1.0"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/shop/registration"));

        List<Shop> shops = shopService.findAll();
        Long id = shops.get(shops.size() - 1).getId();

        assertThat("***").isEqualTo(shopService.findById(id).getName());
        assertThat(false).isEqualTo(shopService.findById(id).isModerated());
        assertThat(false).isEqualTo(shopService.findById(id).isModerateAccept());

        shopService.deleteByIdCascadeEnable(id);
    }
}
