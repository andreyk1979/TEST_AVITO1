package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.FavoriteDao;
import com.amr.project.model.entity.Favorite;
import com.amr.project.service.abstracts.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl extends ReadWriteServiceImpl<Favorite,Long> implements FavoriteService {
    private FavoriteDao favoriteDao;
    @Autowired
    public FavoriteServiceImpl(FavoriteDao favoriteDao) {
        super(favoriteDao);
    }
}
