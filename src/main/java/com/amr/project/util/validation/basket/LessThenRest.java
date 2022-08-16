package com.amr.project.util.validation.basket;

import com.amr.project.util.validation.basket.CountRestValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import static java.lang.annotation.ElementType.TYPE;

@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CountRestValidator.class)
@Documented
public @interface LessThenRest {
    String message() default "{ItemCountPositionDto.countInBasket - invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };
}
