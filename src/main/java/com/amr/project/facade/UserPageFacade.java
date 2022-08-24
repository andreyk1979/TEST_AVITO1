package com.amr.project.facade;


import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import com.amr.project.converter.OrderMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.converter.UserMapper;
import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.OrderDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.OrderService;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.abstracts.UserService;

@Service
@Transactional
public class UserPageFacade {
    private final UserService userService;
    private final UserMapper userMapper;
    private final ShopMapper shopMapper;
    private final ShopService shopService;
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    private Map<ShopDto, String> shopsIsUserMap;
    private Map<OrderDto, Map<ItemDto, String>> orderIsUserMap;
    private Map<ItemDto, String> imageForItemMap;

    public UserPageFacade(UserService userService,
                          UserMapper userMapper,
                          ShopMapper shopMapper,
                          ShopService shopService,
                          OrderService orderService,
                          OrderMapper orderMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.shopMapper = shopMapper;
        this.shopService = shopService;
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    public String showUserPage(Long id, Model model, User user) throws UnsupportedEncodingException {

        model.addAttribute("activeUser", user);

        // Информация о юзере
        UserDto userDto = userMapper.toDto(userService.findById(id));
        model.addAttribute("singleUser", userDto);

        //Конвертация фото пользователя
        ImageDto image = userDto.getImage();
        // У пользователя без картинки вылетало NullPointerException. Поставил условие как заглушку
        if (image == null) {
            image = userMapper.toDto(userService.findById(1L)).getImage();
        }
        byte[] byteImage = Base64.getEncoder().encode(image.getPicture());
        String avatar = new String(byteImage, "UTF-8");
        model.addAttribute("avatarka", avatar);

        // Информация по магазинам и конвертация их логотипа
        shopsIsUserMap = new HashMap<>();
        List<ShopDto> shops = shopMapper.toDtoList(userDto.getShopIds().stream().map(shopService::findById).toList());
        for (ShopDto shop : shops) {
            ImageDto shopImage = shop.getLogo();
            byte[] byteShopImage = Base64.getEncoder().encode(shopImage.getPicture());
            shopsIsUserMap.put(shop, new String(byteShopImage, "UTF-8"));
        }
        model.addAttribute("userShop", shopsIsUserMap);

        //Информация по заказам пользователя, вещам в них и конвертация их изображений
        orderIsUserMap = new HashMap<>();
        imageForItemMap = new HashMap<>();
        List<OrderDto> ordersIsUser = orderMapper.toDtoList(userDto.getOrderIds().stream().map(orderService::findById).toList());
        for (OrderDto order : ordersIsUser) {
            List<ItemDto> itemDtoList = order.getItemsInOrder();
            for (ItemDto item : itemDtoList) {
                ImageDto itemImage = item.getImages().get(0);
                byte[] itemImageByte = Base64.getEncoder().encode(itemImage.getPicture());
                imageForItemMap.put(item, new String(itemImageByte, "UTF-8"));
            }
            orderIsUserMap.put(order, imageForItemMap);
        }

        model.addAttribute("ordersUser", orderIsUserMap);


        return "userPage";
    }
}
