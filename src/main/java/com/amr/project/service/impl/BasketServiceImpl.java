package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.BasketDao;
import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.dto.ItemCountPositionDto;
import com.amr.project.model.entity.Basket;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.ItemCountPosition;
import com.amr.project.service.abstracts.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class BasketServiceImpl extends ReadWriteServiceImpl<Basket,Long> implements BasketService {

    private final BasketDao basketDao;
    private final UserDao userDao;
    @Autowired
    public BasketServiceImpl(BasketDao basketDao, UserDao userDao) {
        super(basketDao);
        this.basketDao = basketDao;
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public void clear(Long id) {
        Basket basket = super.findById(id);
        basket.setItemsCount(new HashMap<>());
        basketDao.update(basket);
    }

    @Transactional
    @Override
    public void updateItemsCounts(Long id, List<ItemCountPosition> positions) {
        Basket basket = super.findById(id);
        basket.setItemsCount(new HashMap<>());
        positions.forEach(el -> basket.getItemsCount().put(el.getItem(), el.getCountInBasket()));
        basketDao.update(basket);
    }

    @Transactional
    @Override
    public void changeOneItemCount(Long id, ItemCountPosition position) {
        Basket basket = super.findById(id);
        basket.getItemsCount().merge(position.getItem(), position.getCountInBasket(), Integer::sum);
        basketDao.update(basket);
    }

    @Transactional
    @Override
    public int decreaseOrDeleteOneItemCount(Long id, ItemCountPosition position) {

        Basket basket = super.findById(id);
        int countInBasket;
        try {
            countInBasket = basket.getItemsCount().get(position.getItem());
        } catch (NullPointerException ex) {
            return 0;
        }
        if(countInBasket <= position.getCountInBasket()) {
            basket.getItemsCount().remove(position.getItem());
            return -1;
        }
        changeOneItemCount(id, new ItemCountPosition(-position.getCountInBasket(), position.getItem()));
        return 1;
    }

    @Override
    @Transactional
    public void deleteOneItemCount(Long id, Item item) {
        Basket basket = super.findById(id);
        basket.getItemsCount().remove(item);
        basketDao.update(basket);
    }

    @Transactional
    public void delete(Basket basket) {
        basket.getUser().setBasket(null);
        userDao.update(basket.getUser());
        basketDao.delete(basket);
    }
}
