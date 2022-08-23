package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Shop;
import java.util.List;

public interface ShopDao extends ReadWriteDao<Shop, Long>{

    List<Shop> findShopList(String name);

    List<Shop> findShopList(String name, Integer page, Integer size);

    List<Shop> getSixMostPopularShop();

    List<Shop> getShopsToBeModerated();

    Long getCountShop(String name);

    List<Shop> getAllShops();
}
