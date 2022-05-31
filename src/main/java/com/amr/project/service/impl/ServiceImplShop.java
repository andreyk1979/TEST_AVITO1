package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.dao.impl.DaoImplShop;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ReadWriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceImplShop extends ReadWriteServiceImpl<Shop, Long> {

    public ServiceImplShop(DaoImplShop dao) {
        super(dao);
    }
}
