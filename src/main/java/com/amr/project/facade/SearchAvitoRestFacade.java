package com.amr.project.facade;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.amr.project.model.dto.ItemShopDto;
import com.amr.project.service.abstracts.ItemShopSearchService;

@Service
@Transactional
public class SearchAvitoRestFacade {

    protected final ItemShopSearchService searchService;

    public SearchAvitoRestFacade(ItemShopSearchService searchService) {
        this.searchService = searchService;
    }

    public ResponseEntity<ItemShopDto> searchString (String name,
                                                     Integer page,
                                                     Integer size,
                                                     Boolean showAll) {
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

    public ResponseEntity<List<Long>> countItemShop(String name){
        List<Long> l = searchService.getCountItemShop(name);
        return new ResponseEntity<>(l, HttpStatus.OK);
    }
}
