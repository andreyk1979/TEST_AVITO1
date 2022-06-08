package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.report.SalesHistory;

import java.util.Calendar;
import java.util.List;

public interface SalesHistoryDao extends ReadWriteDao<SalesHistory, Long> {
    List<SalesHistory> findSalesHistoryFromOrderByShopId(Long id);

    List<SalesHistory> findSalesByDate(Long id, Calendar date1, Calendar date2);

    List<SalesHistory> findSalesByItemName(Long id, String itemName);
}
