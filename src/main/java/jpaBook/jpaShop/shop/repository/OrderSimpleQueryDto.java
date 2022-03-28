package jpaBook.jpaShop.shop.repository;

import jpaBook.jpaShop.shop.domain.Order;
import jpaBook.jpaShop.shop.domain.OrderStatus;
import jpaBook.jpaShop.shop.domain.common.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderSimpleQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDto (Order order) {
        orderId = order.getId();
        name = order.getMember().getName();
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        Address memberAddress = order.getDelivery().getAddress();
        address = new Address(memberAddress.getCity(), memberAddress.getStreet(), memberAddress.getZipcode());
    }
}
