package com.amr.project.model.entity;

import com.amr.project.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@Table(name="order_by_user")
public class Order {
    @Transient
    static final public int EXPIRATION_HOURS = 48; // delete oder if it's not been paid during this time
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "data")
    private Calendar orderDate;//дата заказа

    @Column(name = "delyv_data")
    private Calendar expectedDeliveryDate;//ожидаемая дата доставки

    @Column(name = "total")
    private BigDecimal grandTotal;

    @Column(name = "currency")
    private String currency;

    @Column(name = "descript")
    private String description;//комментарий к заказу со стороны user'a

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private Status status;//статус заказа

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    @Column(name = "qiwi_Id")
    private String qiwiId;

    @ManyToMany
    @JoinTable(name = "order_item",
            joinColumns = @JoinColumn(name = "order_by_user_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    @ToString.Exclude
    private List<Item> itemsInOrder;


    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Address address;

    public Order() {
        qiwiId = UUID.randomUUID().toString();
    }

    // колличество по позиции в заказе (ItemId:Count)
    //@EnoughToLock
    @ToString.Exclude
    @ElementCollection
    @CollectionTable(name="order_position_count",
        joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "item_id")
    @Column(name = "count")
    private Map<Long, Integer> positionCount = new HashMap<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return id != null && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
