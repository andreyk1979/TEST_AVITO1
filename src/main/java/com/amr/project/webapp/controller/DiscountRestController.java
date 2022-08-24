package com.amr.project.webapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.amr.project.facade.DiscountRestFacade;
import com.amr.project.model.dto.DiscountDto;

@RestController
@RequestMapping("api/discount")
public class DiscountRestController {
    private final DiscountRestFacade discountRestFacade;

    public DiscountRestController(DiscountRestFacade discountRestFacade) {
        this.discountRestFacade = discountRestFacade;
    }

    @PostMapping("/discounts")
    @ResponseStatus(HttpStatus.OK)
    public DiscountDto createDiscount(@RequestBody DiscountDto discountDto) {
        return discountRestFacade.createDiscount(discountDto);
    }

    @PutMapping("/discounts")
    @ResponseStatus(HttpStatus.OK)
    public void updateDiscount(@RequestBody DiscountDto discountDto) {
        discountRestFacade.updateDiscount(discountDto);
    }

    @DeleteMapping("/discount/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDiscount(@PathVariable ("id") Long id) {
        discountRestFacade.deleteDiscount(id);
    }

    @GetMapping("/discount")
    @ResponseStatus(HttpStatus.OK)
    public List<DiscountDto> allDiscounts() {
        return discountRestFacade.allDiscounts();
    }

}
