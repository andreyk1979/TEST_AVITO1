package com.amr.project.webapp.controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ReviewMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Review;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ModerationService;
import com.amr.project.service.abstracts.ReviewService;
import com.amr.project.service.abstracts.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restModeration")
public class ModerationRestController {

    private ShopMapper shopMapper;
    private ItemMapper itemMapper;
    private ReviewMapper reviewMapper;
    private ModerationService moderationService;


    @Autowired
    public ModerationRestController(ShopMapper shopMapper, ItemMapper itemMapper, ReviewMapper reviewMapper, ModerationService moderationService) {
        this.shopMapper = shopMapper;
        this.itemMapper = itemMapper;
        this.reviewMapper = reviewMapper;;
        this.moderationService = moderationService;
    }

    @GetMapping("/getShops")
    public List<ShopDto> getShopsToBeModerated() {
        return shopMapper.toDtoList(moderationService.getShopsToBeModerated());
    }

    @GetMapping("/getItems")
    public List<ItemDto> getItemsToBeModerated() {
        return itemMapper.toDtoList(moderationService.getItemsToBeModerated());
    }

    @GetMapping("/getReviews")
    public List<ReviewDto> getReviewsToBeModerated() {
        return reviewMapper.toDtoList(moderationService.getReviewsToBeModerated());
    }

    @PostMapping("/acceptShop/{id}")
    public ShopDto acceptShop(@PathVariable Long id) {
        moderationService.acceptShop(id);

        return moderationService.getShopDto(id);
    }

    @PostMapping("/acceptItem/{id}")
    public ItemDto acceptItem(@PathVariable Long id) {
        moderationService.acceptItem(id);

        return moderationService.getItemDto(id);
    }

    @PostMapping("/acceptReview/{id}")
    public ReviewDto acceptReview(@PathVariable Long id) {
        moderationService.acceptReview(id);

        return moderationService.getReviewDto(id);
    }

    @PostMapping("/declineShop/{id}")
    public ShopDto declineShop(@PathVariable Long id, @RequestBody ShopDto shopDto) {
        moderationService.declineShop(id,shopDto.getDescription());

        return moderationService.getShopDto(id);
    }

    @PostMapping("/declineItem/{id}")
    public ItemDto declineItem(@PathVariable Long id, @RequestBody ItemDto itemDto) {
        moderationService.declineItem(id,itemDto.getDescription());

        return moderationService.getItemDto(id);
    }

    @PostMapping("/declineReview/{id}")
    public ReviewDto declineReview(@PathVariable Long id, @RequestBody ReviewDto reviewDto) {
        moderationService.declineReview(id,reviewDto.getText());

        return moderationService.getReviewDto(id);
    }
}
