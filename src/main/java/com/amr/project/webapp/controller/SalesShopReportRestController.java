package com.amr.project.webapp.controller;

import com.amr.project.converter.GrandSalesMapper;
import com.amr.project.converter.SalesDtoMapper;
import com.amr.project.converter.SalesHistoryMapper;
import com.amr.project.model.dto.report.GrandSalesDto;
import com.amr.project.model.dto.report.SalesDto;
import com.amr.project.service.abstracts.SalesHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sales")
public class SalesShopReportRestController {
    private final SalesHistoryService salesHistoryService;
    private final SalesHistoryMapper salesHistoryMapper;
    private final SalesDtoMapper salesDtoMapper;
    private final GrandSalesMapper grandSalesMapper;

    @Autowired
    public SalesShopReportRestController(SalesHistoryService salesHistoryService,
                                         SalesHistoryMapper salesHistoryMapper,
                                         SalesDtoMapper salesDtoMapper,
                                         GrandSalesMapper grandSalesMapper) {
        this.salesHistoryService = salesHistoryService;
        this.salesHistoryMapper = salesHistoryMapper;
        this.salesDtoMapper = salesDtoMapper;
        this.grandSalesMapper = grandSalesMapper;
    }

    @GetMapping("/{id}/filterByDate")
    public List<SalesDto> getSalesByVariousDates(@PathVariable Long id,
                                       @RequestParam String date1,
                                       @RequestParam String date2) {
        return salesHistoryService.findSalesByVariousDates(id, date1, date2)
                .stream()
                .map(e -> salesDtoMapper.toDto(salesHistoryMapper.toDto(e)))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public List<SalesDto> getAllSales(@PathVariable Long id) {
        return salesHistoryService.getListByShopIdFromOrders(id)
                .stream()
                .map(e -> salesDtoMapper.toDto(salesHistoryMapper.toDto(e)))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/filterByItem")
    public GrandSalesDto getSalesByItemName(@PathVariable Long id, String itemName) {
        return grandSalesMapper.toDto(salesHistoryService.findSalesByItem(itemName, id)
                .stream()
                .map(e -> salesDtoMapper.toDto(salesHistoryMapper.toDto(e)))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}/date")
    public GrandSalesDto getSalesByDate(@PathVariable Long id,
                                        @RequestParam String date) {
       return grandSalesMapper
               .toDto(salesHistoryService.findByDate(id,date)
               .stream()
               .map(e-> salesDtoMapper.toDto(salesHistoryMapper.toDto(e)))
               .collect(Collectors.toList()));
    }


}
