package com.amr.project.dao.impl;

import com.amr.project.converter.ShopMapper;
import com.amr.project.dao.abstracts.ShopDao;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Shop;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ShopDaoImpl extends ReadWriteDaoImpl<ShopDto, Long> implements ShopDao<ShopDto,Long>{
    protected final ShopMapper shopMapper;

    public ShopDaoImpl(ShopMapper shopMapper) {
        this.shopMapper = shopMapper;
    }

    @Override
    public List<ShopDto> findShopList(String name) {
        Query query = em.createQuery("from Shop where lower(name) like :name");
        query.setParameter("name", "%" + name.toLowerCase() + "%");
        List<Shop> shopList = query.getResultList();
        List<ShopDto> shopDtoList = new ArrayList<>();
        for (Shop shop: shopList) {
            shopDtoList.add(shopMapper.toDto(shop, shopMapper.getCouponList(shop)));
        }
        return shopDtoList;
    }
}
