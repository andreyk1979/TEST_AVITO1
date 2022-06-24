package com.amr.project.converter;

import com.amr.project.model.dto.FavoriteDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Favorite;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class FavoriteMapperTest {

    @Autowired
    private FavoriteMapper favoriteMapperTest;

    @Test
    void toFavoriteDto() {
        List<Shop> shop = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        Favorite favorite = new Favorite();
        favorite.setId(1L);
        favorite.setShops(shop);
        favorite.setItems(items);
        FavoriteDto favoriteDto = favoriteMapperTest.toFavoriteDto(favorite);
        Assertions.assertNotNull(favoriteDto);
        Assertions.assertEquals(favorite.getId(), favoriteDto.getId());
        Assertions.assertEquals(favorite.getShops(), favoriteDto.getShops());
        Assertions.assertEquals(favorite.getItems(), favoriteDto.getItems());
    }

    @Test
    void toFavorite() {
        List<ShopDto> shop = new ArrayList<>();
        List<ItemDto> items = new ArrayList<>();
        FavoriteDto favoriteDto = new FavoriteDto();
        favoriteDto.setId(30L);
        favoriteDto.setItems(items);
        favoriteDto.setShops(shop);
        Favorite favorite = favoriteMapperTest.toFavorite(favoriteDto);
        Assertions.assertNotNull(favorite);
        Assertions.assertEquals(favoriteDto.getId(), favorite.getId());
        Assertions.assertEquals(favoriteDto.getShops(), favorite.getShops());
        Assertions.assertEquals(favoriteDto.getItems(), favorite.getItems());
    }
}