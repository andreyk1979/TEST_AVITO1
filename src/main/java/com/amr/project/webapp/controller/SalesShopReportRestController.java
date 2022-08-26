package com.amr.project.webapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.amr.project.facade.SalesShopReportRestFacade;
import com.amr.project.model.dto.report.GrandSalesDto;
import com.amr.project.model.dto.report.SalesHistoryDto;

@RestController
@RequestMapping("/api/sales")
public class SalesShopReportRestController {
    private final SalesShopReportRestFacade salesShopReportRestFacade;

    public SalesShopReportRestController(SalesShopReportRestFacade salesShopReportRestFacade) {
        this.salesShopReportRestFacade = salesShopReportRestFacade;
    }

    @GetMapping("/filterByDate/{id}/{date1}/{date2}")
    public GrandSalesDto getSalesByVariousDates(@PathVariable Long id,
                                       @PathVariable String date1,
                                       @PathVariable String date2) {
        return salesShopReportRestFacade.getSalesByVariousDates(id, date1, date2);
    }

    @GetMapping("/{id}")
    public List<SalesHistoryDto> getAllSales(@PathVariable Long id) {
        return salesShopReportRestFacade.getAllSales(id);
    }

    @GetMapping("/{id}/{itemName}")
    public GrandSalesDto getSalesByItemName(@PathVariable Long id,
                                            @PathVariable String itemName) {
        return salesShopReportRestFacade.getSalesByItemName(id, itemName);
    }

    @GetMapping("/date/{id}/{date}")
    public GrandSalesDto getSalesByDate(@PathVariable Long id,
                                        @PathVariable String date) {
       return salesShopReportRestFacade.getSalesByDate(id, date);
    }


}
