package com.amr.project.webapp.controller;

import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.MainPageDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.MainPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MainPageController {
    @Autowired
    private MainPageService mainPageService;

    @GetMapping("/mainpage")
    public ResponseEntity<MainPageDto> showFourMostPopularItems() {
        return ResponseEntity.ok(mainPageService.getMainPageDto());
    }

}
