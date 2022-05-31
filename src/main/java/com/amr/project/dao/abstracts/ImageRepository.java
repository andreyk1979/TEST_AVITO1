package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Image;
import com.amr.project.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
