package com.amr.project.webapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.amr.project.facade.FavoriteRestFacade;
import com.amr.project.model.dto.FavoriteDto;

@RestController
@RequestMapping("api/favorite")
public class FavoriteRestController {

    private final FavoriteRestFacade favoriteRestFacade;

    public FavoriteRestController(FavoriteRestFacade favoriteRestFacade) {
        this.favoriteRestFacade = favoriteRestFacade;
    }

    @PostMapping("/favorites")
    @ResponseStatus(HttpStatus.OK)
    public FavoriteDto createFavorite(@RequestBody FavoriteDto favoriteDto) {
        return favoriteRestFacade.createFavorite(favoriteDto);
    }
    @PutMapping("/favorites")
    @ResponseStatus(HttpStatus.OK)
    public void updateFavorite(@RequestBody FavoriteDto favoriteDto) {
        favoriteRestFacade.updateFavorite(favoriteDto);
    }

    @DeleteMapping("/favorite/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFavorite(@PathVariable("id") Long id) {
        favoriteRestFacade.deleteFavorite(id);
    }

    @GetMapping("/favorite")
    @ResponseStatus(HttpStatus.OK)
    public List<FavoriteDto> allFavorite() {
        return favoriteRestFacade.allFavorite();
    }

    @GetMapping("/favorite/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FavoriteDto getFavoriteById(@PathVariable Long id) {
        return favoriteRestFacade.getFavoriteById(id);
    }
}
