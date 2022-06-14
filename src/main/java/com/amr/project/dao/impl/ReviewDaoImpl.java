package com.amr.project.dao.impl;


import com.amr.project.dao.abstracts.ReviewDao;
import com.amr.project.model.entity.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ReviewDaoImpl extends ReadWriteDaoImpl<Review, Long> implements ReviewDao {

    @Override
    public List<Review> getReviewsToBeModerated() {

        Query query = em.createQuery("from Review where is_moderated = false");
        return query.getResultList();
    }
}
