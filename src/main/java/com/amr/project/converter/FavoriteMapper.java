package com.amr.project.converter;

import com.amr.project.model.dto.FavoriteDto;
import com.amr.project.model.entity.Favorite;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {
    FavoriteDto toFavoriteDto(Favorite favorite);
    Favorite toFavorite(FavoriteDto favoriteDto);
    List<FavoriteDto> toListFavoriteDto(List<Favorite> favorites);

}
