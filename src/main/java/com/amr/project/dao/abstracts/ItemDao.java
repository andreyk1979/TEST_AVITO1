package com.amr.project.dao.abstracts;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface ItemDao<ItemDto, Long> extends ReadWriteDao<ItemDto, Long> {

    List<ItemDto> findItemList(String name);
}
