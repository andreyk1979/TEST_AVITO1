package com.amr.project.webapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.amr.project.facade.SearchAvitoRestFacade;
import com.amr.project.model.dto.ItemShopDto;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/search")
public class SearchAvitoRestContoller {
    private final SearchAvitoRestFacade searchAvitoRestFacade;

    public SearchAvitoRestContoller(SearchAvitoRestFacade searchAvitoRestFacade) {
        this.searchAvitoRestFacade = searchAvitoRestFacade;
    }

    @ApiOperation(value = "Метод searchString",
            notes = "Метод searchString принемает строку в @PathVariable, а также значения page и size для пагинации в @RequestParam. Для получения всей значений" +
                    "используется @RequestParam Boolean showAll")
    @GetMapping("/{string}")
    public ResponseEntity<ItemShopDto> searchString (@PathVariable("string") String name, @RequestParam(required = false, defaultValue = "2") Integer page, @RequestParam(required = false, defaultValue = "2") Integer size, @RequestParam(required = false, defaultValue = "false") Boolean showAll) {
        return searchAvitoRestFacade.searchString(name, page, size, showAll);
    }

    @ApiOperation(value = "Метод countItemShop",
            notes = "Метод countItemShop принемает строку в @PathVariable и возвращает количество найденных товаров и магазинов List<Long>")
    @GetMapping("/count/{string}")
    public ResponseEntity<List<Long>> countItemShop(@PathVariable("string") String name){
        return searchAvitoRestFacade.countItemShop(name);
    }
}