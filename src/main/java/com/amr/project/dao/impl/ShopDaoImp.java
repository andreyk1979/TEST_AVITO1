package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ShopDaoImp extends ReadWriteDaoImpl<Shop, Long> implements ShopDao {

}
