package com.amr.project.util.validation.basket;

import com.amr.project.converter.ItemMapper;
import com.amr.project.dao.abstracts.BasketDao;
import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.model.dto.ItemCountPositionDto;
import com.amr.project.model.entity.Basket;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CountRestValidator implements ConstraintValidator<LessThenRest, ItemCountPositionDto> {
    @Autowired
    ItemDao itemDao;
    @Autowired
    BasketDao basketDao;
    @Autowired
    ItemMapper itemMapper;

    @Override
    public void initialize(LessThenRest constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    // узнаем актуальный остаток Item в БД и сравниваем с запрошенным + имеющимся в корзине
    public boolean isValid(ItemCountPositionDto itemCountPositionDto, ConstraintValidatorContext constraintValidatorContext) {

        Item item = itemDao.findById(itemCountPositionDto.getItem().getId()).orElseThrow();
        int actualCountRest = item.getCount();
        int requestedCount = itemCountPositionDto.getCountInBasket();
        int countInBasket;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Basket basket = basketDao.findById(user.getId()).orElseThrow();
            countInBasket = basket.getItemsCount().get(itemMapper.toModel(itemCountPositionDto.getItem()));
        } catch (NullPointerException ex) {
            countInBasket = 0;
        }
        return requestedCount + countInBasket <= actualCountRest;
    }
}
