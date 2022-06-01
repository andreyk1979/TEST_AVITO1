package com.amr.project.webapp.controller;

import com.amr.project.converter.*;
import com.amr.project.model.dto.*;
import com.amr.project.model.entity.*;
import com.amr.project.service.abstracts.ItemShopSearchService;
import com.amr.project.service.abstracts.ReadWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/search")
public class SearchAvitoRestContoller {

    protected final ItemShopSearchService searchService;

    public SearchAvitoRestContoller(ItemShopSearchService searchService) {
        this.searchService = searchService;
    }


    @GetMapping("/{string}")
    public ResponseEntity<ItemShopDto> searchString (@PathVariable("string") String name) {
        ItemShopDto itemShopDto = searchService.getItemShopDto(name);
        if (itemShopDto.getItemDtoList().isEmpty() && itemShopDto.getShopDtoList().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(itemShopDto, HttpStatus.OK);
    }
}
