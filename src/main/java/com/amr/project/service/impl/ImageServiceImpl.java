package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ImageDao;
import com.amr.project.model.entity.Image;
import com.amr.project.service.abstracts.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl extends ReadWriteServiceImpl<Image,Long> implements ImageService {

    private ImageDao imageDao;

    @Autowired
    public ImageServiceImpl(ImageDao imageDao) {
        super(imageDao);
    }
}
