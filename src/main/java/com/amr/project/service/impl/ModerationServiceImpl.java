package com.amr.project.service.impl;


import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ReviewMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.dao.abstracts.ReadWriteDao;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModerationServiceImpl extends ReadWriteServiceImpl<Review, Long> implements ModerationService {

    private ReviewService reviewService;
    private ShopService shopService;
    private ItemService itemService;

    private ShopMapper shopMapper;
    private ItemMapper itemMapper;
    private ReviewMapper reviewMapper;

    public ModerationServiceImpl(ReadWriteDao<Review, Long> dao, ReviewService reviewService, ShopService shopService, ItemService itemService, ShopMapper shopMapper, ItemMapper itemMapper, ReviewMapper reviewMapper) {
        super(dao);
        this.reviewService = reviewService;
        this.shopService = shopService;
        this.itemService = itemService;
        this.shopMapper = shopMapper;
        this.itemMapper = itemMapper;
        this.reviewMapper = reviewMapper;
    }

    @Override
    @Transactional
    public List<Shop> getShopsToBeModerated() {
        return shopService.getShopsToBeModerated();
    }

    @Override
    @Transactional
    public List<Item> getItemsToBeModerated() {
        return itemService.getItemsToBeModerated();
    }

    @Override
    @Transactional
    public List<Review> getReviewsToBeModerated() {
        return reviewService.getReviewsToBeModerated();
    }

    @Override
    @Transactional
    public void acceptReview(long id) {
        Review review = reviewService.findById(id);
        review.setModerated(true);
        review.setModerateAccept(true);
        reviewService.update(review);
    }

    @Override
    @Transactional
    public void declineReview(long id, String reason) {
        Review review = reviewService.findById(id);
        review.setModerated(true);
        review.setModerateAccept(false);
        review.setModeratedRejectReason(reason);
        reviewService.update(review);
    }

    @Override
    @Transactional
    public void acceptItem(long id) {
        Item item = itemService.findById(id);
        item.setModerated(true);
        item.setModerateAccept(true);
        itemService.update(item);
    }

    @Override
    @Transactional
    public void declineItem(long id, String reason) {
        Item item = itemService.findById(id);
        item.setModerated(true);
        item.setModerateAccept(false);
        item.setModeratedRejectReason(reason);
        itemService.update(item);
    }

    @Override
    @Transactional
    public void acceptShop(long id) {
        Shop shop = shopService.findById(id);
        shop.setModerated(true);
        shop.setModerateAccept(true);
        shopService.update(shop);
    }

    @Override
    @Transactional
    public void declineShop(long id, String reason) {
        Shop shop = shopService.findById(id);
        shop.setModerated(true);
        shop.setModerateAccept(false);
        shop.setModeratedRejectReason(reason);
        shopService.update(shop);
    }

    @Override
    @Transactional
    public ShopDto getShopDto(long id) {
        return shopMapper.toDto(shopService.findById(id));
    }

    @Override
    @Transactional
    public ItemDto getItemDto(long id) {
        return itemMapper.toDto(itemService.findById(id));
    }

    @Override
    @Transactional
    public ReviewDto getReviewDto(long id) {
        return reviewMapper.toDto(reviewService.findById(id));
    }
}


