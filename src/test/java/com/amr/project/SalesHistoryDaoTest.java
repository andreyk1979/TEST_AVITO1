package com.amr.project;

import com.amr.project.dao.abstracts.SalesHistoryDao;
import com.amr.project.model.entity.report.SalesHistory;
import com.amr.project.model.enums.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SalesHistoryDaoTest {
    @Autowired
    private SalesHistoryDao salesHistoryDao;

    @Test
    public void findSalesHistoryFromOrderByShopIdTest() {
        for (SalesHistory history : salesHistoryDao.findSalesHistoryFromOrderByShopId(1L)) {
            assertThat(history.getItem().getShop().getId()).isEqualTo(1);
            assertThat(history.getItem().getOrders().stream().allMatch(e -> e.getStatus().equals(Status.SENT)))
                    .isEqualTo(true);
        }
    }

    @Test
    public void findSalesByDateTest() {
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        date1.set(2022, Calendar.JUNE, 16, 1, 0);
        date2.set(2022, Calendar.JUNE, 16, 23, 0);
        for (SalesHistory history : salesHistoryDao.findSalesByDate(1L, date1, date2)) {
            assertThat(history.getOrderDate().after(date1)).isEqualTo(true);
            assertThat(history.getOrderDate().before(date2)).isEqualTo(true);
            assertThat(history.getItem().getShop().getId()).isEqualTo(1);
            assertThat(history.getItem().getOrders().stream().allMatch(e -> e.getStatus().equals(Status.SENT)))
                    .isEqualTo(true);
        }
    }

    @Test
    public void findSalesByItemNameTest() {
        String itemName = "Galaxy S20 Ultra";
        for (SalesHistory history : salesHistoryDao.findSalesByItemName(1L, itemName)) {
            assertThat(history.getItem().getName()).isEqualTo(itemName);
            assertThat(history.getItem().getShop().getId()).isEqualTo(1);
            assertThat(history.getItem().getOrders().stream().allMatch(e -> e.getStatus().equals(Status.SENT)))
                    .isEqualTo(true);
        }
    }

    @Test
    public void shouldReturnEmptyList() {
        assertThat(salesHistoryDao.findSalesHistoryFromOrderByShopId(123154L)).isEmpty();
    }
}
