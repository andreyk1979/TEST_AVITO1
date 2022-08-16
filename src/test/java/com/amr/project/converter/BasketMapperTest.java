package com.amr.project.converter;

import com.amr.project.config.TestConfiguration;
import com.amr.project.model.dto.BasketDto;
import com.amr.project.model.entity.Basket;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasketMapperTest {
    @Autowired
    BasketMapper basketMapper;
    ApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);
    User user1 = (User) context.getBean("testUserPrototype");
    User user2 = (User) context.getBean("testUserPrototype");
    Item item1 = (Item) context.getBean("testItemPrototype");
    Item item2 = (Item) context.getBean("testItemPrototype");

    @Before
    public void initRun() {
        item1.setUser(user1);
        item2.setUser(user1);
        user1.getBasket().getItemsCount().put(item1, 4);
        user1.getBasket().getItemsCount().put(item2, 2);
    }
    @Test
    public void toDto() {
        BasketDto basket1Dto = basketMapper.toDto(user1.getBasket());
        BasketDto basket2Dto = basketMapper.toDto(user2.getBasket());

        Assertions.assertNotNull(basket1Dto);
        Assertions.assertEquals(basket1Dto.getId(), user1.getBasket().getId());
        Assertions.assertNotNull(basket1Dto.getItemCountPositions());
        Assertions.assertEquals(basket1Dto.getItemCountPositions().size(), user1.getBasket().getItemsCount().values().size());
        Assertions.assertNotEquals(basket1Dto.getId(), basket2Dto.getId());
    }

    @Test
    public  void  toModel() {
        BasketDto basketDto = basketMapper.toDto(user1.getBasket());
        Basket basketModel = basketMapper.toModel(basketDto);

        Assertions.assertNotNull(basketModel);
        Assertions.assertEquals(basketDto.getId(), basketModel.getId());
        Assertions.assertNotNull(basketDto.getItemCountPositions());
        Assertions.assertEquals(basketDto.getItemCountPositions().size(), basketModel.getItemsCount().values().size());
        Assertions.assertEquals(basketModel.getItemsCount().get(item2), 2);
        Assertions.assertNotNull(basketModel.getUser());
    }
}
