package com.amr.project.model.dto;

import com.amr.project.util.validation.basket.LessThenRest;
import com.amr.project.util.validation.basket.OnAdd;
import com.amr.project.util.validation.basket.OnDec;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@LessThenRest(message = "count in basket is bigger then Item rest", groups = OnAdd.class)
public class ItemCountPositionDto {

    @Min(value = 1, message = "count in basket must be > 0", groups = {OnAdd.class, OnDec.class})
    private int countInBasket;
    private ItemDto item;

}
