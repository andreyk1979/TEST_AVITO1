package com.amr.project.dao.abstracts;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface ShopDao<ShopDto, Long> extends ReadWriteDao<ShopDto, Long>{

    List<ShopDto> findShopList(String name);
}
