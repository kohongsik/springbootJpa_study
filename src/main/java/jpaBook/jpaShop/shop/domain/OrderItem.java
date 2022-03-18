package jpaBook.jpaShop.shop.domain;

import jpaBook.jpaShop.shop.domain.item.Item;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성메서드를 하나로 통일하게 하기위함(new 연산시 에러발생)
@Entity
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name="order_price")
    private int orderPrice; // 주문 가격

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count; // 수량

    // 샹송 메서드 ,생성시 상품의 ㄴ재고수량을 감소시켜야함
    public static OrderItem createOrderItem(Item item, int price, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(price);
        orderItem.setCount(count);
        item.removeStock(count);
        return orderItem;
    }

    // biz logic
    public void cancel() {
        // 재고를 주문 수량만큼 늘려야함
         getItem().addStock(count);
    }

    public int getTotalPrice() {
        return orderPrice * count;
    }
}
