package com.amr.project.converter;

import com.amr.project.model.dto.FavoriteDto;
import com.amr.project.model.entity.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring", uses = {ItemMapper.class, ShopMapper.class})
public interface FavoriteMapper {
    @Mapping(target = "userId", source = "user.id")
    FavoriteDto toFavoriteDto(Favorite favorite);
    Favorite toFavorite(FavoriteDto favoriteDto);
    List<FavoriteDto> toListFavoriteDto(List<Favorite> favorites);

}
