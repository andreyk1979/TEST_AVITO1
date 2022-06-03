package com.amr.project.dao.abstracts;


import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository<Shop, Long> extends ReadWriteDao<Shop, Long> {
    List<Shop> getSixMostPopularShop();
}
