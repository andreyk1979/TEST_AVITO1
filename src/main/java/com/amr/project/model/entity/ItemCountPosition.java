package com.amr.project.model.entity;

import com.amr.project.model.dto.ItemDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

//вспомогательный класс - позиция товара с колличеством в корзине
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemCountPosition {
    int countInBasket;
    Item item;
}
