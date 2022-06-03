package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Image;
import com.amr.project.model.entity.Item;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
public interface ItemDao extends ReadWriteDao<Item, Long>{
    List<Item> getTwoMostPopularItemForShop (Long shopId);
}
