package com.amr.project.converter;

import com.amr.project.model.dto.report.SalesHistoryDto;
import com.amr.project.model.entity.report.SalesHistory;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {ItemMapper.class})
public interface SalesHistoryMapper {
    @Mapping(source = "salesHistory.id", target = "id")
    @Mapping(source = "salesHistory.count", target = "count")
    @Mapping(source = "salesHistory.basePrice", target = "basePrice")
    @Mapping(source = "salesHistory.price", target = "price")
    SalesHistoryDto toDto(SalesHistory salesHistory);
}
