package com.amr.project.model.entity;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "basket")
public class Basket {
    @Id  // user_id @MapsId
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId
    private User user;

    @ToString.Exclude
    @ElementCollection
    @CollectionTable(name="basket_items_count")
    @MapKeyJoinColumn(name="item_id")
    @Column(name = "count")
    private Map<Item, Integer> itemsCount = new HashMap<>();

    public Basket(User user) {
        this.id = user.getId();;
        this.user = user;
        this.user.setBasket(this);
    }
}
