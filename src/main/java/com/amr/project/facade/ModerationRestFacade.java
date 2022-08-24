package com.amr.project.facade;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ReviewMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.service.abstracts.ModerationService;

@Service
@Transactional
public class ModerationRestFacade {

    private final ShopMapper shopMapper;
    private final ItemMapper itemMapper;
    private final ReviewMapper reviewMapper;
    private final ModerationService moderationService;

    public ModerationRestFacade(ShopMapper shopMapper,
                                ItemMapper itemMapper,
                                ReviewMapper reviewMapper,
                                ModerationService moderationService) {
        this.shopMapper = shopMapper;
        this.itemMapper = itemMapper;
        this.reviewMapper = reviewMapper;
        this.moderationService = moderationService;
    }

    public List<ShopDto> getShopsToBeModerated() {
        return shopMapper.toDtoList(moderationService.getShopsToBeModerated());
    }

    public List<ItemDto> getItemsToBeModerated() {
        return itemMapper.toDtoList(moderationService.getItemsToBeModerated());
    }

    public List<ReviewDto> getReviewsToBeModerated() {
        return reviewMapper.toDtoList(moderationService.getReviewsToBeModerated());
    }

    public ShopDto acceptShop(Long id) {
        moderationService.acceptShop(id);

        return moderationService.getShopDto(id);
    }

    public ItemDto acceptItem(Long id) {
        moderationService.acceptItem(id);

        return moderationService.getItemDto(id);
    }

    public ReviewDto acceptReview(Long id) {
        moderationService.acceptReview(id);

        return moderationService.getReviewDto(id);
    }

    public ShopDto declineShop(Long id, ShopDto shopDto) {
        moderationService.declineShop(id,shopDto.getDescription());

        return moderationService.getShopDto(id);
    }

    public ItemDto declineItem(Long id, ItemDto itemDto) {
        moderationService.declineItem(id,itemDto.getDescription());

        return moderationService.getItemDto(id);
    }

    public ReviewDto declineReview(Long id, ReviewDto reviewDto) {
        moderationService.declineReview(id,reviewDto.getText());

        return moderationService.getReviewDto(id);
    }
}
