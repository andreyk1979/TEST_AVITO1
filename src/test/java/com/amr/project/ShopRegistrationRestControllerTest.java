package com.amr.project;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc()
class ShopRegistrationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ShopService shopService;


    @Test
    void shouldAddShopToModerate() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/addShopOnUserPage")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"name\": \"new\",\n" +
                                "    \"description\": \"Официальный магазин new\",\n" +
                                "    \"email\": \"new@gmail.com\",\n" +
                                "    \"phone\": \"new\",\n" +
                                "    \"rating\": 1,\n" +
                                "    \"reviews\": null,\n" +
                                "    \"logo\": null,\n" +
                                "    \"discounts\": null,\n" +
                                "    \"userId\": null,\n" +
                                "    \"couponIds\": [],\n" +
                                "    \"addressDetails\": {\n" +
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
        assertThat("new").isEqualTo(shopService.findById(id).getName());
        assertThat(false).isEqualTo(shopService.findById(id).isModerated());
        assertThat(false).isEqualTo(shopService.findById(id).isModerateAccept());
    }
}
