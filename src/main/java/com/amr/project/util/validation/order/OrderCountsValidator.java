package com.amr.project.util.validation.order;

import com.amr.project.model.entity.Item;
import com.amr.project.service.abstracts.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class OrderCountsValidator implements ConstraintValidator<EnoughToLock, Map<Long, Integer>> {

    ItemService itemService;
    @Autowired
    public OrderCountsValidator(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public void initialize(EnoughToLock constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Map<Long, Integer> positionCounts, ConstraintValidatorContext context) {
        List<String> violations = new ArrayList<>();
        positionCounts.forEach((key, value) -> {
            Item item = itemService.findById(key);
            if (item.getCount() < value) {
                violations.add("requested count more then rest for item: " + key);
            }
            if (value < 1) {
                violations.add("requested count < 1 for item: " + key);
            }
        });
        if (violations.isEmpty()) return true;
        context.disableDefaultConstraintViolation();
        violations.forEach(el -> {
            context.buildConstraintViolationWithTemplate(el).addConstraintViolation();
        });
        return false;
    }
}
