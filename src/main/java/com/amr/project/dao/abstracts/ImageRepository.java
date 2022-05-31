package com.amr.project.dao.abstracts;


import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository<Image, Long> extends ReadWriteDao<Image, Long> {
}
