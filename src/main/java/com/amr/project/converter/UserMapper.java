package com.amr.project.converter;


import com.amr.project.model.entity.*;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import org.mapstruct.InjectionStrategy;

import java.util.UUID;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {AddressMapper.class, ImageMapper.class, ReviewMapper.class, FeedbackMapper.class, FavoriteMapper.class, DiscountMapper.class},
        imports = {Calendar.class, Date.class, LocalDate.class, ZoneId.class, Coupon.class, UUID.class, Collectors.class, Order.class, Shop.class, Chat.class})
public interface UserMapper {

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "phone", source = "user.userInfo.phone")
    @Mapping(target = "firstName", source = "user.userInfo.firstName")
    @Mapping(target = "lastName", source = "user.userInfo.lastName")
    @Mapping(target = "age", source = "user.userInfo.age")
    @Mapping(target = "gender", source = "user.userInfo.gender")
    @Mapping(target = "birthday", source = "user.userInfo.birthday")
    @Mapping(target = "couponIds", expression = "java(user.getCoupons().stream().map(Coupon::getId).collect(Collectors.toList()))")
    @Mapping(target = "orderIds", expression = "java(user.getOrders().stream().map(Order::getId).collect(Collectors.toList()))")
    @Mapping(target = "shopIds", expression = "java(user.getShops().stream().map(Shop::getId).collect(Collectors.toList()))")
    @Mapping(target = "chatIds", expression = "java(user.getChats().stream().map(Chat::getId).collect(Collectors.toSet()))")
    UserDto toDto(User user);

    @Mapping(target = "userInfo.phone", source = "phone")
    @Mapping(target = "userInfo.firstName", source = "firstName")
    @Mapping(target = "userInfo.lastName", source = "lastName")
    @Mapping(target = "userInfo.age", source = "age")
    @Mapping(target = "userInfo.gender", source = "gender")
    @Mapping(target = "userInfo.birthday", source = "birthday")
    @Mapping(target = "secret", defaultExpression = "java(UUID.randomUUID().toString())")
    User toUser(UserDto userDto);

    List<UserDto> toDtos(List<User> users);
}
