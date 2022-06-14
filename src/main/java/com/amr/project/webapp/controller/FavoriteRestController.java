package com.amr.project.webapp.controller;

import com.amr.project.converter.FavoriteMapper;
import com.amr.project.model.dto.FavoriteDto;
import com.amr.project.model.entity.Favorite;
import com.amr.project.service.abstracts.FavoriteService;
import com.amr.project.service.abstracts.ReadWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/favorite")
public class FavoriteRestController {
    private FavoriteService favoriteService;
    private FavoriteMapper favoriteMapper;

    @Autowired
    public FavoriteRestController(FavoriteService favoriteService, FavoriteMapper favoriteMapper) {
        this.favoriteService = favoriteService;
        this.favoriteMapper = favoriteMapper;
    }



    @PostMapping("/favorites")
    @ResponseStatus(HttpStatus.OK)
    public FavoriteDto createFavorite(@RequestBody FavoriteDto favoriteDto) {
        return favoriteMapper.toFavoriteDto(favoriteService.persist(favoriteMapper.toFavorite(favoriteDto)));
    }
    @PutMapping("/favorites")
    @ResponseStatus(HttpStatus.OK)
    public void updateFavorite(@RequestBody FavoriteDto favoriteDto) {
        Favorite newFavorite = favoriteMapper.toFavorite(favoriteDto);
        favoriteService.update(newFavorite);
    }
    @DeleteMapping("/favorite/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFavorite(@PathVariable("id") Long id) {
        favoriteService.deleteByIdCascadeEnable(id);
    }
    @GetMapping("/favorite")
    @ResponseStatus(HttpStatus.OK)
    public List<FavoriteDto> allFavorite() {
        return favoriteMapper.toListFavoriteDto(favoriteService.findAll());
    }
    @GetMapping("/favorite/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FavoriteDto getFavoriteById(@PathVariable Long id) {
        return favoriteMapper.toFavoriteDto(favoriteService.findById(id));
    }
}
