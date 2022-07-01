package com.amr.project.converter;

import com.amr.project.model.dto.DiscountDto;
import com.amr.project.model.entity.Discount;
import com.amr.project.model.entity.Shop;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiscountMapperTest {

    @Autowired
    private DiscountMapper discountMapperTest;

    @Test
    public void toDto() {
        Discount discount = new Discount();
        discount.setId(1L);
        Shop shop = new Shop();
        shop.setId(1L);
        discount.setShop(shop);
        discount.setMinOrder(3);
        DiscountDto discountDto = discountMapperTest.toDto(discount);
        Assertions.assertNotNull(discountDto);
        Assertions.assertEquals(discount.getId(), discountDto.getId());
        Assertions.assertEquals(discount.getShop().getId(), discountDto.getShopId());
        Assertions.assertEquals(discount.getMinOrder(), discountDto.getMinOrder());
    }

    @Test
    public void toModel() {
        DiscountDto discountDto = new DiscountDto();
        discountDto.setId(5L);
        discountDto.setMinOrder(6);
        discountDto.setShopId(7L);
        Shop shop = new Shop();
        shop.setId(7L);
        Discount discount = discountMapperTest.toModel(discountDto);
        discount.setShop(shop);
        Assertions.assertNotNull(discount);
        Assertions.assertEquals(discountDto.getId(), discount.getId());
        Assertions.assertEquals(discountDto.getMinOrder(), discount.getMinOrder());
        Assertions.assertEquals(discountDto.getShopId(), discount.getShop().getId());
    }
}
