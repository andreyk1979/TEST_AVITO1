package com.amr.project.facade;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.amr.project.converter.GrandSalesMapper;
import com.amr.project.converter.SalesDtoMapper;
import com.amr.project.converter.SalesHistoryMapper;
import com.amr.project.model.dto.report.GrandSalesDto;
import com.amr.project.model.dto.report.SalesHistoryDto;
import com.amr.project.service.abstracts.SalesHistoryService;

@Service
@Transactional
public class SalesShopReportRestFacade {

    private final SalesHistoryService salesHistoryService;
    private final SalesHistoryMapper salesHistoryMapper;
    private final SalesDtoMapper salesDtoMapper;
    private final GrandSalesMapper grandSalesMapper;

    public SalesShopReportRestFacade(SalesHistoryService salesHistoryService,
                                     SalesHistoryMapper salesHistoryMapper,
                                     SalesDtoMapper salesDtoMapper,
                                     GrandSalesMapper grandSalesMapper) {
        this.salesHistoryService = salesHistoryService;
        this.salesHistoryMapper = salesHistoryMapper;
        this.salesDtoMapper = salesDtoMapper;
        this.grandSalesMapper = grandSalesMapper;
    }

    public GrandSalesDto getSalesByVariousDates(Long id,
                                                String date1,
                                                String date2) {
        return grandSalesMapper.toDto(salesHistoryService.findSalesByVariousDates(id, date1, date2)
                .stream()
                .map(e -> salesDtoMapper.toDto(salesHistoryMapper.toDto(e)))
                .collect(Collectors.toList()));
    }

    public List<SalesHistoryDto> getAllSales(Long id) {
        return salesHistoryService.getListByShopIdFromOrders(id)
                .stream()
                .map(salesHistoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public GrandSalesDto getSalesByItemName(Long id,
                                            String itemName) {
        return grandSalesMapper.toDto(salesHistoryService.findSalesByItem(itemName, id)
                .stream()
                .map(e -> salesDtoMapper.toDto(salesHistoryMapper.toDto(e)))
                .collect(Collectors.toList()));
    }

    public GrandSalesDto getSalesByDate(Long id,
                                        String date) {
        return grandSalesMapper
                .toDto(salesHistoryService.findByDate(id,date)
                        .stream()
                        .map(e-> salesDtoMapper.toDto(salesHistoryMapper.toDto(e)))
                        .collect(Collectors.toList()));
    }
}
