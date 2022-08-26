package com.amr.project.webapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.amr.project.facade.ShopRestFacade;
import com.amr.project.model.dto.ShopDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/shops")
@Api(description ="REST контроллер для работы с магазинами (model-entity-Shop)")
public class ShopRestController {

    private final ShopRestFacade shopRestFacade;

    public ShopRestController(ShopRestFacade shopRestFacade) {
        this.shopRestFacade = shopRestFacade;
    }

    @GetMapping()
    @ApiOperation(value = "Метод ShowAllShops", notes = "Метод ShowAllShops возвращает List ShopDto - " +
            "список всех магазинов из БД" )
    public List<ShopDto> ShowAllShops() {
        return shopRestFacade.ShowAllShops();
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Метод showSingleShop", notes = "Метод showSingleShop принимает Id магазина из БД " +
            "возвращает ShopDto" )
    public ShopDto showSingleShop(@ApiParam("Id магазина из БД") @PathVariable Long id) {
        return shopRestFacade.showSingleShop(id);
    }

    @PostMapping()
    @ApiOperation(value = "Метод addNewShop", notes = "Метод addNewShop принимает ShopDto из тела request " +
            "сохраняет его в БД и возвращает ShopDto созданного магазина" )
    public ShopDto addNewShop(@ApiParam("ShopDto для добавления магазина в БД") @RequestBody ShopDto shopDto) {
        return shopRestFacade.addNewShop(shopDto);
    }

    @PutMapping()
    @ApiOperation(value = "Метод editShop", notes = "Метод editShop принимает ShopDto из тела request для имеющегося в БД магазина" +
            "изменяет его представление в БД. Ничего не возвращает" )
    public void editShop(@ApiParam("ShopDto для изменения магазина в БД") @RequestBody ShopDto shopDto) {
        shopRestFacade.editShop(shopDto);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Метод deleteShop", notes = "Метод deleteShop принимает Id для имеющегося в БД магазина" +
            " и удаляет его. Ничего не возвращает" )
    public void deleteShop(@ApiParam("Id магазина из БД, который требуется удалить") @PathVariable Long id) {
        shopRestFacade.deleteShop(id);
    }

}
