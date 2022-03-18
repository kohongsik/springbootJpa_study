package jpaBook.jpaShop.shop.service;

import jpaBook.jpaShop.shop.domain.*;
import jpaBook.jpaShop.shop.domain.common.Address;
import jpaBook.jpaShop.shop.domain.item.Item;
import jpaBook.jpaShop.shop.repository.ItemRepository;
import jpaBook.jpaShop.shop.repository.MemberRepository;
import jpaBook.jpaShop.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    // 주문
    @Transactional
    public Long order (Long memberId, Long itemId, int count) {
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);
        Delivery delivery = new Delivery();

        // 배송정보 생성
        delivery.setAddress(member.getAddress()); // setter overriding
        delivery.setStatus(DeliveryStatus.READY);

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);
        // notify : cascade 범위 설정은 라이프사이클 상에서 동일하게 관리하면서 다른곳에서 참조가 안일어날때 번위 정도 ?
        return orderRepository.save(order); // cascate 옵션으로 인하여 member, order 자동 생성/업데이트
    }
    // 취소
    @Transactional
    public void cancelOrder (Long orderId) {
        // db 조회
        Order order = orderRepository.findOne(orderId);
        order.cancel(); // java단에서 수정만해도 자동으로 update 반영
    }
    // 검색
    public List<Order> findOrders (OrderSearch orderSearch) {
        return null;
    }

    public List<Order> findAllByString (OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }
}
