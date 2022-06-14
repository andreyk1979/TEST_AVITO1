package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.SalesHistoryDao;
import com.amr.project.model.entity.report.SalesHistory;
import com.amr.project.service.abstracts.SalesHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SalesHistoryServiceImpl implements SalesHistoryService {
    private final SalesHistoryDao salesHistoryDao;

    @Autowired
    public SalesHistoryServiceImpl(SalesHistoryDao salesHistoryDao) {
        this.salesHistoryDao = salesHistoryDao;
    }

    @Override
    public List<SalesHistory> findSalesByVariousDates(Long idOfShop, String dateAfter, String dateBefore) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1;
        Date date2;
        Calendar calendar1;
        Calendar calendar2;
        try {
            date1 = sdf.parse(dateAfter);
            date2 = sdf.parse(dateBefore);
            calendar1 = Calendar.getInstance();
            calendar2 = Calendar.getInstance();
            calendar1.setTime(date1);
            calendar2.setTime(date2);
            return salesHistoryDao.findSalesByDate(idOfShop, calendar1, calendar2);
        } catch (ParseException e) {
            try {
                throw new ParseException("Date is incorrect", e.getErrorOffset());
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public List<SalesHistory> getListByShopIdFromOrders(Long id) {
        return salesHistoryDao.findSalesHistoryFromOrderByShopId(id);
    }

    @Override
    public List<SalesHistory> findSalesByItem(String itemName, Long id) {
        Map<Calendar, SalesHistory> map = new HashMap<>();
        for (SalesHistory history : salesHistoryDao.findSalesByItemName(id, itemName)) {
            Calendar name = history.getOrderDate();
            if (!map.containsKey(name)) {
                map.put(name, history);
            } else if (map.get(name).getOrderDate().equals(history.getOrderDate())) {
                SalesHistory s = map.get(name);
                s.setCount(map.get(name).getCount() + history.getCount());
                s.setPrice(map.get(name).getPrice().add(history.getPrice()));
                s.setBasePrice(map.get(name).getBasePrice().add(history.getBasePrice()));
                map.put(name, s);
            }
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public List<SalesHistory> findByDate(Long id, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1;
        Calendar calendar1;
        Calendar calendar2;
        try {
            date1 = sdf.parse(date);
            calendar1 = Calendar.getInstance();
            calendar2 = Calendar.getInstance();
            calendar1.setTime(date1);
            calendar1.set(Calendar.HOUR_OF_DAY, 1);
            calendar2.setTime(date1);
            calendar2.set(Calendar.HOUR_OF_DAY, 23);
            return salesHistoryDao.findSalesByDate(id, calendar1, calendar2);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

}
