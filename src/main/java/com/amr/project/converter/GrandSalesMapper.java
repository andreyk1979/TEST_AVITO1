package com.amr.project.converter;

import com.amr.project.model.dto.report.GrandSalesDto;
import com.amr.project.model.dto.report.SalesDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {SalesDtoMapper.class})
public interface GrandSalesMapper {

    default GrandSalesDto toDto(List<SalesDto> salesDto) {
        BigDecimal profit = new BigDecimal(0);
        BigDecimal totalSum = new BigDecimal(0);
        for (SalesDto sales : salesDto) {
            totalSum = totalSum.add(sales.getTotalSum());
            profit = profit.add(sales.getTotalProfit());
        }
        return GrandSalesDto.builder()
                .sales(salesDto)
                .grandTotalProfit(profit)
                .grandTotalSum(totalSum)
                .build();
    }
}
