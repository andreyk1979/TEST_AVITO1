package com.amr.project.service.abstracts;

import com.amr.project.model.entity.report.SalesHistory;

import java.util.List;

public interface SalesHistoryService {
    List<SalesHistory> findSalesByVariousDates(Long idOfShop, String dayAfter, String dayBefore);

    List<SalesHistory> getListByShopIdFromOrders(Long id);

    List<SalesHistory> findSalesByItem(String itemName, Long id);

    List<SalesHistory> findByDate(Long id, String date);

}
