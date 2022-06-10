package com.amr.project.webapp.controller;

import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.service.abstracts.*;
import com.amr.project.model.entity.Shop;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shops")
@Api(description ="REST контроллер для работы с магазинами (model-entity-Shop)")
public class ShopRestController {

    private final ShopService shopService;
    private final ShopMapper shopMapper;

    @Autowired
    public ShopRestController(ShopMapper shopMapper, ShopService shopService) {
        this.shopMapper = shopMapper;
        this.shopService = shopService;
    }

    @GetMapping()
    @ApiOperation(value = "Метод ShowAllShops", notes = "Метод ShowAllShops возвращает List ShopDto - " +
            "список всех магазинов из БД" )
    public List<ShopDto> ShowAllShops() {
        return shopMapper.toDtoList(shopService.findAll());
    }

    @GetMapping("{id}")
    @ApiOperation(value = "Метод showSingleShop", notes = "Метод showSingleShop принимает Id магазина из БД " +
            "возвращает ShopDto" )
    public ShopDto showSingleShop(@ApiParam("Id магазина из БД") @PathVariable Long id) {
        return shopMapper.toDto(shopService.findById(id));
    }

    @PostMapping()
    @ApiOperation(value = "Метод addNewShop", notes = "Метод addNewShop принимает ShopDto из тела request " +
            "сохраняет его в БД и возвращает ShopDto созданного магазина" )
    public ShopDto addNewShop(@ApiParam("ShopDto для добавления магазина в БД") @RequestBody ShopDto shopDto) {
        return shopMapper.toDto(shopService
                .persist(shopMapper.toModel(shopDto)));
    }

    @PutMapping()
    @ApiOperation(value = "Метод editShop", notes = "Метод editShop принимает ShopDto из тела request для имеющегося в БД магазина" +
            "изменяет его представление в БД. Ничего не возвращает" )
    public void editShop(@ApiParam("ShopDto для изменения магазина в БД") @RequestBody ShopDto shopDto) {
        Shop newShop = shopMapper.toModel(shopDto);
        shopService.update(newShop);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "Метод deleteShop", notes = "Метод deleteShop принимает Id для имеющегося в БД магазина" +
            " и удаляет его. Ничего не возвращает" )
    public void deleteShop(@ApiParam("Id магазина из БД, который требуется удалить") @PathVariable Long id) {
        shopService.deleteByIdCascadeIgnore(id);
    }

}
