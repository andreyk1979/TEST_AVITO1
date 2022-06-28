package com.amr.project.webapp.controller;

import com.amr.project.converter.DiscountMapper;
import com.amr.project.model.dto.DiscountDto;
import com.amr.project.model.entity.Discount;
import com.amr.project.service.abstracts.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/discount")
public class DiscountRestController {
    private final DiscountService discountService;
    private final DiscountMapper discountMapper;

    @Autowired
    public DiscountRestController(DiscountService discountService, DiscountMapper discountMapper) {
        this.discountService = discountService;
        this.discountMapper = discountMapper;
    }

    @PostMapping("/discounts")
    @ResponseStatus(HttpStatus.OK)
    public DiscountDto createDiscount(@RequestBody DiscountDto discountDto) {
        return discountMapper.toDto(discountService.persist(discountMapper.toModel(discountDto)));
    }

    @PutMapping("/discounts")
    @ResponseStatus(HttpStatus.OK)
    public void updateDiscount(@RequestBody DiscountDto discountDto) {
        Discount discount = discountMapper.toModel(discountDto);
        discountService.update(discount);
    }

    @DeleteMapping("/discount/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDiscount(@PathVariable ("id") Long id) {
        discountService.deleteByIdCascadeEnable(id);
    }

    @GetMapping("/discount")
    @ResponseStatus(HttpStatus.OK)
    public List<DiscountDto> allDiscounts() {
        return discountMapper.tolist(discountService.findAll());
    }

}
