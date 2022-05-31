package com.amr.project.dao.abstracts;


import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository<Review, Long> extends ReadWriteDao<Review, Long> {

}
