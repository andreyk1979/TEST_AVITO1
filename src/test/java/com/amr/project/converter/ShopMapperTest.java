package com.amr.project.converter;

import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Shop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ShopMapperTest {
    @Autowired
    private ShopMapper shopMapper;

    @Test
    void toModel() {
        ShopDto dto = ShopDto.builder()
                .id(1L)
                .description("description")
                .name("Vasya")
                .email("tets@mail.com")
                .build();

        Shop entity = shopMapper.toModel(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getEmail(), entity.getEmail());
    }
}