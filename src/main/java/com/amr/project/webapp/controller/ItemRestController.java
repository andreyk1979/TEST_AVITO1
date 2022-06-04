package com.amr.project.webapp.controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Item;
import com.amr.project.service.abstracts.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/item")
public class ItemRestController {

    private final ItemService itemService;

    private final ItemMapper itemMapper;

    @Autowired
    public ItemRestController(ItemService itemService, ItemMapper itemMapper) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> showAllItems(){
        return itemService.findAll().stream().map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto showItemById(@PathVariable Long id){
        return itemMapper.toDto(itemService.findById(id));
    }
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ItemDto createItem(@RequestBody ItemDto itemDto){
        return itemMapper.toDto(itemService.persist(itemMapper.toModel(itemDto)));
    }
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateItem(@RequestBody ItemDto itemDto) {
        Item item = itemMapper.toModel(itemDto);
        itemService.update(item);
    }

    /**
     * Метод помечает товар на удаление используя поле
     * isPretendedToBeDelete сущности Item
     * @param id
     */

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void markForDeleteItem(@PathVariable Long id) {
        itemService.isPretendedToBeDeleted(id);
    }

//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    //@Secured("ROLE_ADMIN")
//    //Снять коментарий после включения security
//    public void deleteItem(@PathVariable Long id) {
//        itemService.deleteByIdCascadeEnable(id);
//    }

}
