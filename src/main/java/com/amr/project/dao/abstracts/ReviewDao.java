package com.amr.project.dao.abstracts;

import org.springframework.stereotype.Repository;

@Repository
public interface ReviewDao<Review, Long> extends ReadWriteDao<Review, Long> {

}
