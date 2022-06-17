package com.amr.project.webapp.controller;

import com.amr.project.service.abstracts.ItemShopSearchService;
import com.amr.project.model.dto.ItemShopDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchAvitoRestContoller {

    protected final ItemShopSearchService searchService;

    public SearchAvitoRestContoller(ItemShopSearchService searchService) {
        this.searchService = searchService;
    }

    @ApiOperation(value = "Метод searchString",
            notes = "Метод searchString принемает строку в @PathVariable, а также значения page и size для пагинации в @RequestParam. Для получения всей значений" +
                    "используется @RequestParam Boolean showAll")
    @GetMapping("/{string}")
    public ResponseEntity<ItemShopDto> searchString (@PathVariable("string") String name, @RequestParam(required = false, defaultValue = "2") Integer page, @RequestParam(required = false, defaultValue = "2") Integer size, @RequestParam(required = false, defaultValue = "false") Boolean showAll) {
        ItemShopDto itemShopDto;
        if(showAll){
            itemShopDto = searchService.getItemShopDto(name);
        } else {
            itemShopDto = searchService.getItemShopDto(name, page, size);
        }

        if (itemShopDto.getItemDtoList().isEmpty() && itemShopDto.getShopDtoList().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(itemShopDto, HttpStatus.OK);
    }

    @ApiOperation(value = "Метод countItemShop",
            notes = "Метод countItemShop принемает строку в @PathVariable и возвращает количество найденных товаров и магазинов List<Long>")
    @GetMapping("/count/{string}")
    public ResponseEntity<List<Long>> countItemShop(@PathVariable("string") String name){
        List<Long> l = searchService.getCountItemShop(name);
        return new ResponseEntity<>(l, HttpStatus.OK);
    }
}