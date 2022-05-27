package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findTop4ByOrderByRatingDesc();
}
