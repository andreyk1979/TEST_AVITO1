package com.amr.project.converter;

import com.amr.project.model.dto.report.SalesDto;
import com.amr.project.model.dto.report.SalesHistoryDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {SalesHistoryMapper.class})
public interface SalesDtoMapper {
    @Mapping(target = "item", expression = "java(salesHistory.getItem().getName())")
    @Mapping(target = "basePrice", expression = "java(salesHistory.getBasePrice())")
    @Mapping(target = "totalSum",
            expression = "java(salesHistory.getPrice().multiply(java.math.BigDecimal.valueOf(salesHistory.getCount())))")
    @Mapping(target = "profit", expression = "java(salesHistory.getPrice().subtract(salesHistory.getBasePrice()))")
    @Mapping(target = "totalProfit", expression = "java(salesHistory.getPrice().subtract(salesHistory.getBasePrice()).multiply(java.math.BigDecimal.valueOf(salesHistory.getCount())))")
    SalesDto toDto(SalesHistoryDto salesHistory);


}
