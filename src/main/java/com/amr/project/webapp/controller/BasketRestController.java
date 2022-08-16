package com.amr.project.webapp.controller;

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
import com.amr.project.util.validation.basket.OnAdd;
import com.amr.project.util.validation.basket.OnDec;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping(path = "/api/basket", produces={"application/json"})
//@CrossOrigin(origins={"http://localhost:8888"}) // todo makeev - for vuejs, delete if doesnt use
public class BasketRestController {

    final private BasketServiceImpl basketService;
    final private ItemService itemService;
    final private ItemCountPositionMapper itemCountPositionMapper;
    final private ItemMapper itemMapper;
    final private BasketMapper basketMapper;

    @Autowired
    public BasketRestController(BasketServiceImpl basketService, ItemService itemService,
                                ItemCountPositionMapper itemCountPositionMapper, ItemMapper itemMapper,
                                BasketMapper basketMapper) {
        this.basketService = basketService;
        this.itemService = itemService;
        this.itemCountPositionMapper = itemCountPositionMapper;
        this.itemMapper = itemMapper;
        this.basketMapper = basketMapper;
    }

    @ApiOperation(value= "Метод addOneItem", notes= "Метод принимает JSON ItemCountPositionDto " +
            "(из Item нужен только id) валидирует поле countInBasket,создает или увеличивает " +
            "позицию в корзине или возвращает ошибку валидации ")
    @PutMapping("/item/add")
    @Validated(OnAdd.class)
    public ResponseEntity<HttpStatus> addOneItem(@AuthenticationPrincipal User user,
                                       @Valid @RequestBody ItemCountPositionDto itemCountPositionDto) {

        basketService.changeOneItemCount(user.getId(), itemCountPositionMapper.toModel(itemCountPositionDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value= "Метод decreaseOneItem", notes= "Метод принимает JSON ItemCountPositionDto " +
            "(из Item нужен только id) валидирует поле countInBasket, убавляет позицию в корзине, " +
            "при достижении 0 удаляет ее из корзины. возвращает результат операции Map<id, res>  " +
            "(res: 1 - убавлено, -1 удалено, 0 - попытка удалить не существующую в корзине поз)")
    @PutMapping("/item/dec")
    @Validated({OnDec.class})
    public Map<Long, Integer> decreaseOneItem(@AuthenticationPrincipal User user,
                                                      @Valid @RequestBody ItemCountPositionDto itemCountPositionDto) {
        int result = basketService.decreaseOrDeleteOneItemCount(user.getId(),
                itemCountPositionMapper.toModel(itemCountPositionDto));
        return Collections.singletonMap(user.getId(), result);
    }

    @ApiOperation(value= "Метод getActualCounts", notes= "Метод принимает JSON List<ItemDto> " +
            "(из Item нужен только id) возвращает актуальные остатки Map<id:count>")
    @GetMapping("/rests")
    public Map<Long, Integer> getActualCounts(@RequestBody List<ItemDto> itemDtos) {
        return itemDtos.stream()
                .collect(Collectors.toMap(
                        el -> el.getId(),
                        el -> itemService.findById(el.getId()).getCount()
                ));
    }

    @ApiOperation(value= "Метод getBasket", notes= "Метод возвращает упрощенный Map корзины (id: count) ")
    @GetMapping("/items/")
    public Map<Long, Integer> getBasket(@AuthenticationPrincipal User user) {

        BasketDto basketDto = basketMapper.toDto(basketService.findById(user.getId()));
        return basketDto.getItemCountPositions().stream()
                .collect(Collectors.toMap(
                        el -> el.getItem().getId(),
                        el -> el.getCountInBasket()
                ));
    }

    @ApiOperation(value= "Метод updateItem", notes= "Метод принимает JSON List<ItemCountPositionDto> " +
            "(из Item нужен только id) валидирует поля countInBasket,заменяет позиции в корзине или " +
            "возвращает ошибку валидации")
    @PutMapping("/items/")
    public ResponseEntity<HttpStatus> updateItems(@AuthenticationPrincipal User user,
                                        @Valid @RequestBody List<ItemCountPositionDto> itemCountPositionDtos) {

        basketService.updateItemsCounts(user.getId(), itemCountPositionDtos.stream()
                .map(itemCountPositionMapper::toModel).toList());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value= "Метод clearBasket", notes = "удаляет все записи из корзины, но не саму корзину")
    @DeleteMapping("/items")
    public ResponseEntity<HttpStatus> clearBasket(@AuthenticationPrincipal User user) {

        basketService.clear(user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value= "Метод deleteOneItem", notes = "Принимает JSON ItemCountPositionDto (из Item нужен только id)" +
            "Удаляет конкретную запись из корзины")
    @DeleteMapping("/item")
    public ResponseEntity<HttpStatus> deleteOneItem(@AuthenticationPrincipal User user, @RequestBody ItemDto itemDto) {

        basketService.deleteOneItemCount(user.getId(), itemMapper.toModel(itemDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<ErrorMessage> handleValidationException(ConstraintViolationException exception) {
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<ErrorMessage> handleMethodArgumentException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(new ErrorMessage(400, new Date(), ex.getMessage(), ex.getNestedPath()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<ErrorMessage> userNotFound(NotFoundException ex) {
        return new ResponseEntity<>(new ErrorMessage(403, new Date(), ex.getMessage(), Arrays.toString(ex.getStackTrace())),
                HttpStatus.BAD_REQUEST);
    }
}
