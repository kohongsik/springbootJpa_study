package jpaBook.jpaShop.shop.domain.item;

import jpaBook.jpaShop.shop.domain.Category;
import jpaBook.jpaShop.shop.exception.NonEnoughStockException;
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
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    @Builder.Default
    private List<Category> categories = new ArrayList<>();

    // biz logic <- 가지고 있는 객체안에 비즈니스 메서드를 서술하는게 좋음
    // 재고 수량 증가
    public void addStock (int quantity) {
        this.stockQuantity += quantity;
    }
    // 재고 수량 감소
    public void removeStock (int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NonEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
