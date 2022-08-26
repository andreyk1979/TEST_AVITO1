package com.amr.project.facade;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.amr.project.converter.ItemMapper;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Item;
import com.amr.project.service.abstracts.ItemService;

@Service
@Transactional
public class ItemRestFacade {

    private final ItemService itemService;

    private final ItemMapper itemMapper;

    public ItemRestFacade(ItemService itemService, ItemMapper itemMapper) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    public List<ItemDto> showAllItems(){
        return itemService.findAll().stream().map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    public ItemDto showItemById(Long id){
        return itemMapper.toDto(itemService.findById(id));
    }

    public ItemDto createItem(ItemDto itemDto){
        return itemMapper.toDto(itemService.persist(itemMapper.toModel(itemDto)));
    }

    public void updateItem(ItemDto itemDto) {
        Item item = itemMapper.toModel(itemDto);
        itemService.update(item);
    }

    public void deleteItem(Long id) {
        itemService.deleteByIdCascadeEnable(id);
    }
}
