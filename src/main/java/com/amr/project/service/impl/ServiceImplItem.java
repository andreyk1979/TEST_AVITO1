package com.amr.project.service.impl;

import com.amr.project.dao.impl.DaoImplItem;
import com.amr.project.dao.impl.DaoImplShop;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ReadWriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceImplItem extends ReadWriteServiceImpl<Item, Long> {

    public ServiceImplItem(DaoImplItem dao) {
        super(dao);
    }
}

