package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.dao.abstracts.ReviewDao;
import com.amr.project.model.entity.Review;
import com.amr.project.service.abstracts.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl extends ReadWriteServiceImpl<Review, Long> implements ReviewService {

    private final ReviewDao reviewDao;

    @Autowired
    public ReviewServiceImpl(ReadWriteDao<Review, Long> dao, ReviewDao reviewDao) {
        super(dao);
        this.reviewDao = reviewDao;
    }

    @Override
    public List<Review> getReviewsToBeModerated() {
        return reviewDao.getReviewsToBeModerated();
    }
}
