package com.amr.project.webapp.controller;

import com.amr.project.converter.CityMapper;
import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ItemShopDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Coupon;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ReadWriteService;
import com.amr.project.service.impl.ReadWriteServiceImpl;
import com.amr.project.service.impl.ServiceImplItem;
import com.amr.project.service.impl.ServiceImplShop;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/search")
public class SearchAvitoRestContoller {

    @PersistenceContext
    private EntityManager em;

    private ReadWriteService<Item, Long> serviceImplItem = new ReadWriteServiceImpl<>(new ReadWriteDao<Item, Long>() {
        @Override
        public void persist(Item entity) {
            em.persist(entity);
        }

        @Override
        public void update(Item entity) {
            em.merge(entity);
        }

        @Override
        public void delete(Item entity) {
            em.remove(em.contains(entity) ? entity : em.merge(entity));
        }

        @Override
        public void deleteByIdCascadeEnable(Long id) {
            em.remove(em.find(Item.class,id));
        }

        @Override
        public void deleteByIdCascadeIgnore(Long id) {
            em.createQuery("DELETE FROM " + Item.class.getName() + " u WHERE u.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
        }

        @Override
        public boolean existsById(Long id) {
            return em.find(Item.class, id) != null;
        }

        @Override
        public Item findById(Long id) {
            return em.find(Item.class, id);
        }

        @Override
        public List<Item> findAll() {
            return em.createQuery("select u from " + Item.class.getName() + " u", Item.class)
                    .getResultList();
        }
    });
    private ReadWriteService<Shop, Long> serviceImplShop = new ReadWriteServiceImpl(new ReadWriteDao<Shop, Long>() {
        @Override
        public void persist(Shop entity) {
            em.persist(entity);
        }

        @Override
        public void update(Shop entity) {
            em.merge(entity);
        }

        @Override
        public void delete(Shop entity) {
            em.remove(em.contains(entity) ? entity : em.merge(entity));
        }

        @Override
        public void deleteByIdCascadeEnable(Long id) {
            em.remove(em.find(Shop.class,id));
        }

        @Override
        public void deleteByIdCascadeIgnore(Long id) {
            em.createQuery("DELETE FROM " + Shop.class.getName() + " u WHERE u.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
        }

        @Override
        public boolean existsById(Long id) {
            return em.find(Shop.class, id) != null;
        }

        @Override
        public Shop findById(Long id) {
            return em.find(Shop.class, id);
        }

        @Override
        public List<Shop> findAll() {
            return em.createQuery("select u from " + Shop.class.getName() + " u", Shop.class)
                    .getResultList();
        }
    });
//
//    protected final ServiceImplItem serviceImplItem;
//    protected final ServiceImplShop serviceImplShop;
//
//    public SearchAvitoRestContoller(ServiceImplItem serviceImplItem, ServiceImplShop serviceImplShop) {
//        this.serviceImplItem = serviceImplItem;
//        this.serviceImplShop = serviceImplShop;
//    }


    @GetMapping("/{string}")
    public ResponseEntity<ItemShopDto> searchString (@PathVariable("string") String name) {
        List<Item> item = serviceImplItem.findAll();
        List<Shop> shops = serviceImplShop.findAll();
        List<ItemDto> dtoItemList = new ArrayList<>();
        List<ShopDto> dtoShopList = new ArrayList<>();
        for (Item nameItem : item) {
            if (nameItem.getName().contains(name.toLowerCase())) {
                ItemDto itemDto = ItemMapper.INSTANCE.toDto(nameItem);
                dtoItemList.add(itemDto);
            }
        }
        for (Shop shop : shops) {
            if (shop.getName().contains(name.toLowerCase())) {
                List<Long> coupons = new ArrayList<>();
                for (Coupon coupon : shop.getCoupons()) {
                    coupons.add(coupon.getId());
                }
                ShopDto shopDto = ShopMapper.INSTANCE
                        .toDto(shop, CityMapper.INSTANCE.toDto(shop.getAddress().getCity()), coupons);
                dtoShopList.add(shopDto);
            }
        }
        if (dtoItemList.isEmpty() && dtoShopList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new ItemShopDto(dtoShopList, dtoItemList), HttpStatus.OK);
    }
}
