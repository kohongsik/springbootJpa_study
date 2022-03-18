package jpaBook.jpaShop.shop.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderSearch {
    private String memberName; // 주문자명
    private OrderStatus orderStatus; // 주문 상태 [ORDE, CANCEL]

}
