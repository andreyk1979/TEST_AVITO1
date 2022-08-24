package com.amr.project.webapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.amr.project.facade.ReviewRestFacade;
import com.amr.project.model.dto.ReviewDto;

@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {

    private final ReviewRestFacade reviewRestFacade;

    public ReviewRestController(ReviewRestFacade reviewRestFacade) {
        this.reviewRestFacade = reviewRestFacade;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public ReviewDto createReview(@RequestBody ReviewDto reviewDto) {
        return reviewRestFacade.createReview(reviewDto);
    }

    @GetMapping("/{itemId}")
    public List<ReviewDto> getReviewsForItem(@PathVariable Long itemId) {
        return reviewRestFacade.getReviewsForItem(itemId);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteItemReview(@PathVariable Long reviewId) {
        reviewRestFacade.deleteItemReview(reviewId);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void editReview(@RequestBody ReviewDto reviewDto) {
        reviewRestFacade.editReview(reviewDto);
    }
}
