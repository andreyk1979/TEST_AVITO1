package com.amr.project.webapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.amr.project.facade.ItemRestFacade;
import com.amr.project.model.dto.ItemDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "REST контроллер для работы с товаром(Item)")
@RestController
@RequestMapping("/api/item")
public class ItemRestController {

    private final ItemRestFacade itemRestFacade;

    public ItemRestController(ItemRestFacade itemRestFacade) {
        this.itemRestFacade = itemRestFacade;
    }

    @ApiOperation(value = "Метод showAllItems",
            notes = "Метод showAllItems возвращает List всех ItemDto")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> showAllItems(){
        return itemRestFacade.showAllItems();
    }

    @ApiOperation(value = "Метод showItemById",
            notes = "Метод showItemById принимает id в @PathVariable и возвращает соответствующий ItemDto")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto showItemById(@PathVariable Long id){
        return itemRestFacade.showItemById(id);
    }

    @ApiOperation(value = "Метод createItem",
            notes = "Метод createItem принимает @RequestBody ItemDto записывает его в базу и возвращает ItemDto")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ItemDto createItem(@RequestBody ItemDto itemDto){
        return itemRestFacade.createItem(itemDto);
    }
    @ApiOperation(value = "Метод updateItem",
            notes = "Метод updateItem принимает @RequestBody ItemDto обновляет его в базе и возвращает void")
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateItem(@RequestBody ItemDto itemDto) {
        itemRestFacade.updateItem(itemDto);
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
        itemRestFacade.deleteItem(id);
    }

}