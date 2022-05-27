package com.amr.project.service.abstracts;

import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MainPageService {
    List<ItemDto> showFourMostPopularItems();
    List<ShopDto> showSixMostPopularShops();
}
