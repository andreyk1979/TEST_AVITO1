package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.BasketDao;
import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.entity.Basket;
import com.amr.project.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BasketDaoImpl extends ReadWriteDaoImpl<Basket, Long> implements BasketDao {

    UserDao userDao;
    @Autowired
    public BasketDaoImpl(UserDao userDao) {
        this.userDao = userDao;
    }
}
