package com.amr.project.webapp.controller;

import com.amr.project.converter.ReviewMapper;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.entity.Review;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;
    private final ItemService itemService;

    @Autowired
    public ReviewRestController(ReviewService reviewService, ReviewMapper reviewMapper, ItemService itemService) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
        this.itemService = itemService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public ReviewDto createReview(@RequestBody ReviewDto reviewDto) {
        return reviewMapper.toDto(reviewService.persist(reviewMapper.toModel(reviewDto)));
    }

    @GetMapping("/{itemId}")
    public List<ReviewDto> getReviewsForItem(@PathVariable Long itemId) {
        return reviewMapper.toDtoList(itemService.findById(itemId).getReviews());
    }

    @DeleteMapping("/{reviewId}")
    public void deleteItemReview(@PathVariable Long reviewId) {
        reviewService.deleteByIdCascadeIgnore(reviewId);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void editReview(@RequestBody ReviewDto reviewDto) {
        Review review = reviewMapper.toModel(reviewDto);
        reviewService.update(review);
    }
}
