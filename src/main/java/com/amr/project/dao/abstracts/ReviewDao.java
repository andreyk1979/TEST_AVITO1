package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Review;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewDao extends ReadWriteDao<Review, Long> {

    List<Review> getReviewsToBeModerated();
}
