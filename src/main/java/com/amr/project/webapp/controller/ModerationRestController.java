package com.amr.project.webapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.amr.project.facade.ModerationRestFacade;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.dto.ShopDto;

@RestController
@RequestMapping("/restModeration")
public class ModerationRestController {

    private final ModerationRestFacade moderationRestFacade;

    public ModerationRestController(ModerationRestFacade moderationRestFacade) {
        this.moderationRestFacade = moderationRestFacade;
    }

    @GetMapping("/getShops")
    public List<ShopDto> getShopsToBeModerated() {
        return moderationRestFacade.getShopsToBeModerated();
    }

    @GetMapping("/getItems")
    public List<ItemDto> getItemsToBeModerated() {
        return moderationRestFacade.getItemsToBeModerated();
    }

    @GetMapping("/getReviews")
    public List<ReviewDto> getReviewsToBeModerated() {
        return moderationRestFacade.getReviewsToBeModerated();
    }

    @PostMapping("/acceptShop/{id}")
    public ShopDto acceptShop(@PathVariable Long id) {
        return moderationRestFacade.acceptShop(id);
    }

    @PostMapping("/acceptItem/{id}")
    public ItemDto acceptItem(@PathVariable Long id) {
        return moderationRestFacade.acceptItem(id);
    }

    @PostMapping("/acceptReview/{id}")
    public ReviewDto acceptReview(@PathVariable Long id) {
        return  moderationRestFacade.acceptReview(id);
    }

    @PostMapping("/declineShop/{id}")
    public ShopDto declineShop(@PathVariable Long id, @RequestBody ShopDto shopDto) {
        return moderationRestFacade.declineShop(id, shopDto);
    }

    @PostMapping("/declineItem/{id}")
    public ItemDto declineItem(@PathVariable Long id, @RequestBody ItemDto itemDto) {
        return moderationRestFacade.declineItem(id, itemDto);
    }

    @PostMapping("/declineReview/{id}")
    public ReviewDto declineReview(@PathVariable Long id, @RequestBody ReviewDto reviewDto) {
        return moderationRestFacade.declineReview(id, reviewDto);
    }
}
