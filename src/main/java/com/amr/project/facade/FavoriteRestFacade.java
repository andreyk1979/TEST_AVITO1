package com.amr.project.facade;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.amr.project.converter.FavoriteMapper;
import com.amr.project.model.dto.FavoriteDto;
import com.amr.project.model.entity.Favorite;
import com.amr.project.service.abstracts.FavoriteService;

@Service
@Transactional
public class FavoriteRestFacade {

    private final FavoriteService favoriteService;
    private final FavoriteMapper favoriteMapper;

    public FavoriteRestFacade(FavoriteService favoriteService, FavoriteMapper favoriteMapper) {
        this.favoriteService = favoriteService;
        this.favoriteMapper = favoriteMapper;
    }

    public FavoriteDto createFavorite(FavoriteDto favoriteDto) {
        return favoriteMapper.toFavoriteDto(favoriteService.persist(favoriteMapper.toFavorite(favoriteDto)));
    }

    public void updateFavorite(FavoriteDto favoriteDto) {
        Favorite newFavorite = favoriteMapper.toFavorite(favoriteDto);
        favoriteService.update(newFavorite);
    }

    public void deleteFavorite(Long id) {
        favoriteService.deleteByIdCascadeEnable(id);
    }

    public List<FavoriteDto> allFavorite() {
        return favoriteMapper.toListFavoriteDto(favoriteService.findAll());
    }

    public FavoriteDto getFavoriteById(Long id) {
        return favoriteMapper.toFavoriteDto(favoriteService.findById(id));
    }
}
