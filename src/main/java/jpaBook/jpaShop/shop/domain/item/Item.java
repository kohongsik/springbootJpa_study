package jpaBook.jpaShop.shop.domain.item;

import jpaBook.jpaShop.shop.domain.Category;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn()
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @Column(name="item_name")
    private String name;
    @Column(name="stock_quantity")
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

}