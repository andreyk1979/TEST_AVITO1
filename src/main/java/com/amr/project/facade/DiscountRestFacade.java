package com.amr.project.facade;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.amr.project.converter.DiscountMapper;
import com.amr.project.model.dto.DiscountDto;
import com.amr.project.model.entity.Discount;
import com.amr.project.service.abstracts.DiscountService;

@Service
@Transactional
public class DiscountRestFacade {

    private final DiscountService discountService;
    private final DiscountMapper discountMapper;

    public DiscountRestFacade(DiscountService discountService, DiscountMapper discountMapper) {
        this.discountService = discountService;
        this.discountMapper = discountMapper;
    }

    public DiscountDto createDiscount(DiscountDto discountDto) {
        return discountMapper.toDto(discountService.persist(discountMapper.toModel(discountDto)));
    }

    public void updateDiscount(DiscountDto discountDto) {
        Discount discount = discountMapper.toModel(discountDto);
        discountService.update(discount);
    }

    public void deleteDiscount(Long id) {
        discountService.deleteByIdCascadeEnable(id);
    }

    public List<DiscountDto> allDiscounts() {
        return discountMapper.tolist(discountService.findAll());
    }
}
