package com.amr.project.webapp.controller;

import java.util.List;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.amr.project.exception.ErrorMessage;
import com.amr.project.facade.BasketRestFacade;
import com.amr.project.model.dto.ItemCountPositionDto;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.entity.User;
import com.amr.project.util.validation.basket.OnAdd;
import com.amr.project.util.validation.basket.OnDec;

import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

@Validated
@RestController
@RequestMapping(path = "/api/basket", produces = {"application/json"})
//@CrossOrigin(origins={"http://localhost:8888"}) // todo makeev - for vuejs, delete if doesnt use
public class BasketRestController {

    private final BasketRestFacade basketRestFacade;

    public BasketRestController(BasketRestFacade basketRestFacade) {
        this.basketRestFacade = basketRestFacade;
    }

    @ApiOperation(value = "Метод addOneItem", notes = "Метод принимает JSON ItemCountPositionDto " +
            "(из Item нужен только id) валидирует поле countInBasket,создает или увеличивает " +
            "позицию в корзине или возвращает ошибку валидации ")
    @PutMapping("/item/add")
    @Validated(OnAdd.class)
    public ResponseEntity<HttpStatus> addOneItem(@AuthenticationPrincipal User user,
                                                 @Valid @RequestBody ItemCountPositionDto itemCountPositionDto) {

        return basketRestFacade.addOneItem(user, itemCountPositionDto);
    }

    @ApiOperation(value = "Метод decreaseOneItem", notes = "Метод принимает JSON ItemCountPositionDto " +
            "(из Item нужен только id) валидирует поле countInBasket, убавляет позицию в корзине, " +
            "при достижении 0 удаляет ее из корзины. возвращает результат операции Map<id, res>  " +
            "(res: 1 - убавлено, -1 удалено, 0 - попытка удалить не существующую в корзине поз)")
    @PutMapping("/item/dec")
    @Validated({OnDec.class})
    public Map<Long, Integer> decreaseOneItem(@AuthenticationPrincipal User user,
                                              @Valid @RequestBody ItemCountPositionDto itemCountPositionDto) {
        return basketRestFacade.decreaseOneItem(user, itemCountPositionDto);
    }

    @ApiOperation(value = "Метод getActualCounts", notes = "Метод принимает JSON List<ItemDto> " +
            "(из Item нужен только id) возвращает актуальные остатки Map<id:count>")
    @PostMapping("/rests")
    public Map<Long, Integer> getActualCounts(@RequestBody List<ItemDto> itemDtos) {
        return basketRestFacade.getActualCounts(itemDtos);
    }

    @ApiOperation(value = "Метод getBasket", notes = "Метод возвращает упрощенный Map корзины (id: count) ")
    @GetMapping("/items")
    public Map<Long, Integer> getBasket(@AuthenticationPrincipal User user) {

        return basketRestFacade.getBasket(user);
    }

    @ApiOperation(value = "Метод updateItem", notes = "Метод принимает JSON List<ItemCountPositionDto> " +
            "(из Item нужен только id) валидирует поля countInBasket,заменяет позиции в корзине или " +
            "возвращает ошибку валидации")
    @PutMapping("/items")
    @Validated({OnAdd.class, OnDec.class})
    public ResponseEntity<HttpStatus> updateItems(@AuthenticationPrincipal User user,
                                                  @Valid @RequestBody List<ItemCountPositionDto> itemCountPositionDtos) {
        return basketRestFacade.updateItems(user, itemCountPositionDtos);
    }

    @ApiOperation(value = "Метод clearBasket", notes = "удаляет все записи из корзины, но не саму корзину")
    @DeleteMapping("/items")
    public ResponseEntity<HttpStatus> clearBasket(@AuthenticationPrincipal User user) {
        return basketRestFacade.clearBasket(user);
    }

    @ApiOperation(value = "Метод deleteOneItem", notes = "Принимает JSON ItemCountPositionDto (из Item нужен только id)" +
            "Удаляет конкретную запись из корзины")
    @DeleteMapping("/item")
    public ResponseEntity<HttpStatus> deleteOneItem(@AuthenticationPrincipal User user, @RequestBody ItemDto itemDto) {
        return basketRestFacade.deleteOneItem(user, itemDto);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<ErrorMessage> handleValidationException(ConstraintViolationException exception) {
        return  basketRestFacade.handleValidationException(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<ErrorMessage> handleMethodArgumentException(MethodArgumentNotValidException ex) {
        return basketRestFacade.handleMethodArgumentException(ex);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<ErrorMessage> userNotFound(NotFoundException ex) {
        return basketRestFacade.userNotFound(ex);
    }
}
