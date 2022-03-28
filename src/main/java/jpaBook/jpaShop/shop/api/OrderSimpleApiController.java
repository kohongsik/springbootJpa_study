package jpaBook.jpaShop.shop.api;

import jpaBook.jpaShop.shop.domain.Order;
import jpaBook.jpaShop.shop.domain.OrderSearch;
import jpaBook.jpaShop.shop.domain.OrderStatus;
import jpaBook.jpaShop.shop.domain.common.Address;
import jpaBook.jpaShop.shop.repository.OrderRepository;
import jpaBook.jpaShop.shop.repository.OrderSimpleQueryDto;
import jpaBook.jpaShop.shop.utils.ApiUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import jpaBook.jpaShop.shop.utils.ApiUtils.ResponseApiEntity;

import static jpaBook.jpaShop.shop.utils.ApiUtils.success;
/* x to one
* order -> delivery
* order -> member
* */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    @GetMapping(value = "/api/v1/simple-orders")
    public List<Order> orderV1 () {

        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
        }
        return all;
    }
    @GetMapping(value = "/api/v2/simple-orders")
    public ResponseApiEntity<List<SimpleOrderDto>> orderV2 () {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDto> simpleOrderDtos = orders.stream().map(SimpleOrderDto::new).collect(Collectors.toList());
        return success(simpleOrderDtos);
    }
    @GetMapping(value = "/api/v3/simple-orders")
    public ResponseApiEntity<List<SimpleOrderDto>> orderV3 () {
        List<Order> orders = orderRepository.findAllByStringWithMemberDelivery(new OrderSearch());
        List<SimpleOrderDto> simpleOrderDtos = orders.stream().map(SimpleOrderDto::new).collect(Collectors.toList());
        return success(simpleOrderDtos);
    }

    @GetMapping(value = "/api/v4/simple-orders")
    public ResponseApiEntity<List<OrderSimpleQueryDto>> orderV4 () {
        List<OrderSimpleQueryDto> orders = orderRepository.findAllByStringDto(new OrderSearch());
        return success(orders);
    }
    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto (Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            Address memberAddress = order.getDelivery().getAddress();
            address = new Address(memberAddress.getCity(), memberAddress.getStreet(), memberAddress.getZipcode());
        }
    }
}
