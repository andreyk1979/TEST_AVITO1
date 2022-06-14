package com.amr.project.service.impl;


import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ReviewMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Review;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ModerationService;
import com.amr.project.dao.abstracts.ItemDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.dao.abstracts.ReviewDao;
import com.amr.project.dao.abstracts.ShopDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModerationServiceImpl extends ReadWriteServiceImpl<Review, Long> implements ModerationService {


    private ReviewDao reviewDao;
    private ShopDao shopDao;
    private ItemDao itemDao;

    private ShopMapper shopMapper;
    private ItemMapper itemMapper;
    private ReviewMapper reviewMapper;

    @Autowired
    public ModerationServiceImpl(ReadWriteDao<Review, Long> dao, ReviewDao reviewDao, ShopDao shopDao, ItemDao itemDao, ShopMapper shopMapper, ItemMapper itemMapper, ReviewMapper reviewMapper) {
        super(dao);
        this.reviewDao = reviewDao;
        this.shopDao = shopDao;
        this.itemDao = itemDao;
        this.shopMapper = shopMapper;
        this.itemMapper = itemMapper;
        this.reviewMapper = reviewMapper;
    }

    @Override
    @Transactional
    public List<Shop> getShopsToBeModerated() {
        return shopDao.getShopsToBeModerated();
    }

    @Override
    @Transactional
    public List<Item> getItemsToBeModerated() {
        return itemDao.getItemsToBeModerated();
    }

    @Override
    @Transactional
    public List<Review> getReviewsToBeModerated() {
        return reviewDao.getReviewsToBeModerated();
    }

    @Override
    @Transactional
    public void acceptReview(long id) {
        Review review = reviewDao.findById(id);
        review.setModerated(true);
        review.setModerateAccept(true);
        reviewDao.update(review);
    }

    @Override
    @Transactional
    public void declineReview(long id, String reason) {
        Review review = reviewDao.findById(id);
        review.setModerated(true);
        review.setModerateAccept(false);
        review.setModeratedRejectReason(reason);
        reviewDao.update(review);
    }

    @Override
    @Transactional
    public void acceptItem(long id) {
        Item item = itemDao.findById(id);
        item.setModerated(true);
        item.setModerateAccept(true);
        itemDao.update(item);
    }

    @Override
    @Transactional
    public void declineItem(long id, String reason) {
        Item item = itemDao.findById(id);
        item.setModerated(true);
        item.setModerateAccept(false);
        item.setModeratedRejectReason(reason);
        itemDao.update(item);
    }

    @Override
    @Transactional
    public void acceptShop(long id) {
        Shop shop = shopDao.findById(id);
        shop.setModerated(true);
        shop.setModerateAccept(true);
        shopDao.update(shop);
    }

    @Override
    @Transactional
    public void declineShop(long id, String reason) {
        Shop shop = shopDao.findById(id);
        shop.setModerated(true);
        shop.setModerateAccept(false);
        shop.setModeratedRejectReason(reason);
        shopDao.update(shop);
    }

    @Override
    @Transactional
    public ShopDto getShopDto(long id) {
        return shopMapper.toDto(shopDao.findById(id));
    }

    @Override
    @Transactional
    public ItemDto getItemDto(long id) {
        return itemMapper.toDto(itemDao.findById(id));
    }

    @Override
    @Transactional
    public ReviewDto getReviewDto(long id) {
        return reviewMapper.toDto(reviewDao.findById(id));
    }
}


