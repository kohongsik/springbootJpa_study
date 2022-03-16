package jpaBook.jpaShop.shop.domain;

import jpaBook.jpaShop.shop.domain.item.Item;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name="order_price")
    private int orderPrice; // 주문 가격

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int count; // 수량
}
