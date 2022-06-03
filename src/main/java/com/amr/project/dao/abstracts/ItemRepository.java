package com.amr.project.dao.abstracts;

import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository<Item, Long> extends ReadWriteDao<Item, Long> {

   void isPretendedToBeDeleted(Long id);
}
