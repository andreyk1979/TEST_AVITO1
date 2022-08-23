package com.amr.project.webapp.controller;

import com.amr.project.converter.BasketMapper;
import com.amr.project.converter.OrderMapper;
import com.amr.project.model.dto.BasketDto;
import com.amr.project.model.dto.ItemCountPositionDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.entity.Order;
import com.amr.project.model.entity.User;
import com.amr.project.model.enums.Status;
import com.amr.project.service.abstracts.BasketService;
import com.amr.project.service.abstracts.PayService;
import com.amr.project.service.impl.OrderServiceImpl;
import com.amr.project.service.impl.UserServiceImp;
import com.qiwi.billpayments.sdk.model.out.BillResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderMapper orderMapper;
    private final UserServiceImp userServiceImp;
    private final OrderServiceImpl orderService;
    private final PayService<BillResponse> payService;
    private final BasketMapper basketMapper;
    private final BasketService basketService;


    @Autowired
    public OrderController(OrderMapper orderMapper,
                               UserServiceImp userServiceImp,
                               OrderServiceImpl orderService,
                               PayService<BillResponse> payService, BasketMapper basketMapper, BasketService basketService) {

        this.orderMapper = orderMapper;
        this.userServiceImp = userServiceImp;
        this.orderService = orderService;
        this.payService = payService;
        this.basketMapper = basketMapper;
        this.basketService = basketService;
    }

    @PostMapping
    public String createOrder(@Valid /*@RequestBody*/@ModelAttribute("orderDto") OrderDto orderDto, @AuthenticationPrincipal User user, Model model) {
        orderDto.setDate(LocalDateTime.ofInstant(Calendar.getInstance().toInstant(), Calendar.getInstance().getTimeZone().toZoneId())); // костыль для deliveryDate
        BasketDto basketDto = basketMapper.toDto(basketService.findById(user.getId()));
        List<ItemDto> itemDtoList = new ArrayList<>();
        for (ItemCountPositionDto item : basketDto.getItemCountPositions()) {
            itemDtoList.add(item.getItem());
        }
        orderDto.setItemsInOrder(itemDtoList);

        Order order = orderMapper.toModel(orderDto);
        order.setAddress(user.getAddress());
        order.setUser(user);
        order.setStatus(Status.START);
        order.setOrderDate(Calendar.getInstance());
        order.setCurrency("RUB");
        order.setGrandTotal(orderDto.getTotal());

        // TODO добавить логику ожидаемой даты заказа
        Calendar deliveryDate = Calendar.getInstance();
        deliveryDate.setTime(Date.from(orderDto.getDate().atZone(ZoneId.systemDefault()).toInstant()));
        order.setExpectedDeliveryDate(deliveryDate);
        orderService.update(order);

        // makeev
        // create order using createOrderFromBasket
        // you don't need orderDto in your @ModelAttribute

        // replace rows 81-81 by next rows (deliveryDate also will be set there)

        // OrderDto orderDto = new OrderDto();
        // orderService.setPositionCountFromBasket(orderDto, user.getId());
        // orderService.lockItemsRests(orderDto);
        // Order order = orderService.createOrderFromBasket(orderDto, user);

        model.addAttribute("activeUser", user);
        return "/orderPage";
    }
}
