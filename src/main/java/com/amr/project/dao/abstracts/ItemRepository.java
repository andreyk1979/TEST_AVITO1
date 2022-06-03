package com.amr.project.dao.abstracts;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository<Item, Long> extends ReadWriteDao<Item, Long> {

    List<Item> getFourMostPopularItem();

    void isPretendedToBeDeleted(Long id);
}

