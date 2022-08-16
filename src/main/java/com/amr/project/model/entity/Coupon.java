package com.amr.project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Coupon {
    //TODO разовый скидочный купон для покупателя, добавить поля (например размер скидки)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "start")
    private Calendar start;

    @Future(message = "Дата окончания должна быть позднее текущей даты")
    @Column(name = "end")
    private Calendar end;

    @Column(name = "isUsed", nullable = false)
    private boolean isUsed = false;

    @Min(value=0, message = "Скидка должна быть болльше 0")
    @Column(name = "discount", nullable = false)
    private int discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Coupon coupon = (Coupon) o;
        return id != null && Objects.equals(id, coupon.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
