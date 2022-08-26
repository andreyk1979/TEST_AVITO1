package com.amr.project.converter;

import com.amr.project.model.dto.BasketDto;
import com.amr.project.model.dto.ItemCountPositionDto;
import com.amr.project.model.entity.Basket;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.BasketService;
import com.amr.project.service.abstracts.UserService;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class BasketMapper {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private UserService userService;

    public BasketDto  toDto(Basket basket) {
        List<ItemCountPositionDto> itemCountPositionDtos = basket.getItemsCount().entrySet().stream()
                .map(el -> new ItemCountPositionDto(el.getValue(), itemMapper.toDto(el.getKey()))).toList();
        return new BasketDto(basket.getId(), itemCountPositionDtos);
    }

    public Basket toModel(BasketDto basketDto) {
        Map<Item, Integer> itemsCount = basketDto.getItemCountPositions().stream()
                .collect(Collectors.toMap(key -> itemMapper.toModel(key.getItem()), ItemCountPositionDto::getCountInBasket));
        Basket basket = new Basket();
        basket.setId(basketDto.getId());
        basket.setUser(userService.findById(basketDto.getId()));
        basket.setItemsCount(itemsCount);
        return basket;
    }
}


