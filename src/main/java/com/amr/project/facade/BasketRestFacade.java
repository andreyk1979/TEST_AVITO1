package com.amr.project.facade;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.amr.project.converter.BasketMapper;
import com.amr.project.converter.ItemCountPositionMapper;
import com.amr.project.converter.ItemMapper;
import com.amr.project.exception.ErrorMessage;
import com.amr.project.model.dto.BasketDto;
import com.amr.project.model.dto.ItemCountPositionDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.impl.BasketServiceImpl;

import javassist.NotFoundException;

@Service
@Transactional
public class BasketRestFacade {

    final private BasketServiceImpl basketService;
    final private ItemService itemService;
    final private ItemCountPositionMapper itemCountPositionMapper;
    final private ItemMapper itemMapper;
    final private BasketMapper basketMapper;

    public BasketRestFacade(BasketServiceImpl basketService,
                            ItemService itemService,
                            ItemCountPositionMapper itemCountPositionMapper,
                            ItemMapper itemMapper,
                            BasketMapper basketMapper) {
        this.basketService = basketService;
        this.itemService = itemService;
        this.itemCountPositionMapper = itemCountPositionMapper;
        this.itemMapper = itemMapper;
        this.basketMapper = basketMapper;
    }

    public ResponseEntity<HttpStatus> addOneItem(User user,
                                                 ItemCountPositionDto itemCountPositionDto) {
        basketService.changeOneItemCount(user.getId(), itemCountPositionMapper.toModel(itemCountPositionDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Map<Long, Integer> decreaseOneItem(User user,
                                               ItemCountPositionDto itemCountPositionDto) {
        int result = basketService.decreaseOrDeleteOneItemCount(user.getId(),
                itemCountPositionMapper.toModel(itemCountPositionDto));
        return Collections.singletonMap(user.getId(), result);
    }

    public Map<Long, Integer> getActualCounts(List<ItemDto> itemDtos) {
        return itemDtos.stream()
                .collect(Collectors.toMap(
                        el -> el.getId(),
                        el -> itemService.findById(el.getId()).getCount()
                ));
    }

    public Map<Long, Integer> getBasket(User user) {
        BasketDto basketDto = basketMapper.toDto(basketService.findById(user.getId()));
        return basketDto.getItemCountPositions().stream()
                .collect(Collectors.toMap(
                        el -> el.getItem().getId(),
                        el -> el.getCountInBasket()
                ));
    }

    public ResponseEntity<HttpStatus> updateItems(User user,
                                                   List<ItemCountPositionDto> itemCountPositionDtos) {
        basketService.updateItemsCounts(user.getId(), itemCountPositionDtos.stream()
                .map(itemCountPositionMapper::toModel).toList());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> clearBasket(User user) {
        basketService.clear(user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteOneItem(User user, ItemDto itemDto) {
        basketService.deleteOneItemCount(user.getId(), itemMapper.toModel(itemDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<ErrorMessage> handleValidationException(ConstraintViolationException exception) {
        StringBuilder message = new StringBuilder();
        StringBuilder description = new StringBuilder();
        exception.getConstraintViolations().forEach(error -> {
            message.append(error.getMessage());
            message.append(";");
            description.append(error.getInvalidValue().toString());
            description.append(";");
        });
        return new ResponseEntity<>(new ErrorMessage(
                400, new Date(), message.toString(), description.toString()),
                HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ErrorMessage> handleMethodArgumentException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(new ErrorMessage(400, new Date(), ex.getMessage(), ex.getNestedPath()),
                HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ErrorMessage> userNotFound(NotFoundException ex) {
        return new ResponseEntity<>(new ErrorMessage(403, new Date(), ex.getMessage(), Arrays.toString(ex.getStackTrace())),
                HttpStatus.BAD_REQUEST);
    }
}
