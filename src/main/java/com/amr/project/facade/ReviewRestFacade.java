package com.amr.project.facade;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.amr.project.converter.ReviewMapper;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.entity.Review;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ReviewService;

@Service
@Transactional
public class ReviewRestFacade {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;
    private final ItemService itemService;

    public ReviewRestFacade(ReviewService reviewService, ReviewMapper reviewMapper, ItemService itemService) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
        this.itemService = itemService;
    }

    public ReviewDto createReview(ReviewDto reviewDto) {
        return reviewMapper.toDto(reviewService.persist(reviewMapper.toModel(reviewDto)));
    }

    public List<ReviewDto> getReviewsForItem(Long itemId) {
        return reviewMapper.toDtoList(itemService.findById(itemId).getReviews());
    }

    public void deleteItemReview(Long reviewId) {
        reviewService.deleteByIdCascadeIgnore(reviewId);
    }

    public void editReview(ReviewDto reviewDto) {
        Review review = reviewMapper.toModel(reviewDto);
        reviewService.update(review);
    }
}
