package com.amr.project.dao.abstracts;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface ShopDao<Shop, Long> extends ReadWriteDao<Shop, Long>{

    List<Shop> findShopList(String name);
}
