package com.amr.project.service.abstracts;

import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Feedback;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Review;
import com.amr.project.model.entity.Shop;

import java.util.List;

public interface ModerationService {

    List<Shop> getShopsToBeModerated();

    List<Item> getItemsToBeModerated();

    List<Review> getReviewsToBeModerated();

    void acceptReview(long id);

    void declineReview(long id, String reason);

    void acceptItem(long id);

    void declineItem(long id, String reason);

    void acceptShop(long id);

    void declineShop(long id, String reason);

    ShopDto getShopDto(long id);

    ItemDto getItemDto(long id);

    ReviewDto getReviewDto(long id);
}
