package com.amr.project.webapp.controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.Item;
import com.amr.project.service.abstracts.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Api(description = "REST контроллер для работы с товаром(Item)")
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
    @ApiOperation(value = "Метод showAllItems",
            notes = "Метод showAllItems возвращает List всех ItemDto")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> showAllItems(){
        return itemService.findAll().stream().map(itemMapper::toDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Метод showItemById",
            notes = "Метод showItemById принимает id в @PathVariable и возвращает соответствующий ItemDto")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto showItemById(@PathVariable Long id){
        return itemMapper.toDto(itemService.findById(id));
    }

    @ApiOperation(value = "Метод createItem",
            notes = "Метод createItem принимает @RequestBody ItemDto записывает его в базу и возвращает ItemDto")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ItemDto createItem(@RequestBody ItemDto itemDto){
        return itemMapper.toDto(itemService.persist(itemMapper.toModel(itemDto)));
    }
    @ApiOperation(value = "Метод updateItem",
            notes = "Метод updateItem принимает @RequestBody ItemDto обновляет его в базе и возвращает void")
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
//    @ApiOperation(value = "Метод markForDeleteItem",
//            notes = "Метод markForDeleteItem помечает товары на удаление. принимает id в @PathVariable и ставит значение true в поле isPretendedToBeDeleted")
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public void markForDeleteItem(@PathVariable Long id) {
//        itemService.isPretendedToBeDeleted(id);
//    }

    @ApiOperation(value = "Метод deleteItem(ROLE_ADMIN)",
            notes = "Метод deleteItem удаляет товар по id @PathVariable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    //@Secured("ROLE_ADMIN")
    //Снять коментарий после включения security
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteByIdCascadeEnable(id);
    }

}