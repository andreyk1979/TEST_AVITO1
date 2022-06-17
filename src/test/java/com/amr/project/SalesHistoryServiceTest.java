package com.amr.project;

import com.amr.project.dao.abstracts.SalesHistoryDao;
import com.amr.project.model.entity.report.SalesHistory;
import com.amr.project.service.abstracts.SalesHistoryService;
import com.amr.project.service.impl.SalesHistoryServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.*;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SalesHistoryServiceTest {

    private SalesHistoryService salesHistoryService;

    private SalesHistoryDao salesHistoryDaoMock;

    @Before
    public void initTest() {
        salesHistoryDaoMock = mock(SalesHistoryDao.class);
        salesHistoryService = new SalesHistoryServiceImpl(salesHistoryDaoMock);
    }

    @Test
    public void findSalesByVariousDatesTest() {
        Calendar date1 = Calendar.getInstance();
        date1.set(2022, Calendar.JUNE, 1);
        Calendar date2 = Calendar.getInstance();
        date2.set(2022, Calendar.JUNE, 30);
        String dateForService1 = "2022-06-01";
        String dateForService2 = "2022-06-30";
        List<SalesHistory> listFromMock = salesHistoryDaoMock.findSalesByDate(1L, date1, date2);
        List<SalesHistory> listFromService = salesHistoryService.findSalesByVariousDates(1L, dateForService1, dateForService2);
        assertThat(listFromMock).isEqualTo(listFromService);
        for (SalesHistory history : listFromService) {
            assertThat(history.getOrderDate().after(date1)).isEqualTo(true);
            assertThat(history.getOrderDate().before(date2)).isEqualTo(true);
        }
    }

    @Test
    public void getListByShopIdFromOrdersTest() {
        List<SalesHistory> listFromMock = salesHistoryDaoMock.findSalesHistoryFromOrderByShopId(1L);
        List<SalesHistory> listFromService = salesHistoryService.getListByShopIdFromOrders(1L);
        assertThat(listFromMock).isEqualTo(listFromService);
    }

    @Test
    public void findSalesByItemTest() {
        String itemName = "Galaxy S20 Ultra";
        List<SalesHistory> listFromService = salesHistoryService.findSalesByItem(itemName, 1L);
        for (SalesHistory salesHistory : listFromService) {
            assertThat(salesHistory.getItem().getName()).isEqualTo(itemName);
        }
    }

    @Test
    public void findByDate() {
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        date1.set(2022, Calendar.JUNE, 16, 1, 0);
        date1.set(2022, Calendar.JUNE, 16, 23, 0);
        List<SalesHistory> listFromMock = salesHistoryDaoMock.findSalesByDate(1L, date1, date2);
        List<SalesHistory> listFromService = salesHistoryService.findByDate(1L, "2022-06-16");
        assertThat(listFromMock).isEqualTo(listFromService);
    }

    @Test
    public void shouldReturnEmptyListFindByDate() {
        assertThat(salesHistoryService.findByDate(1L, "2021-01-01")).isEmpty();
    }

    @Test
    public void shouldReturnEmptyListFindByItemName() {
        assertThat(salesHistoryService.findSalesByItem("no item in DB", 1L)).isEmpty();
    }

    @Test
    public void shouldReturnEmptyListFindByVariousDates() {
        assertThat(salesHistoryService.findSalesByVariousDates(1L, "2021-01-01", "2021-05-05")).isEmpty();
    }

    @Test
    public void shouldThrowRuntimeException() {
        Assert.assertThrows(RuntimeException.class, () -> salesHistoryService.findByDate(1L, "231"));
    }
}
