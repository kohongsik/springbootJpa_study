package jpaBook.jpaShop.shop.service;

import jpaBook.jpaShop.shop.domain.Member;
import jpaBook.jpaShop.shop.domain.Order;
import jpaBook.jpaShop.shop.domain.OrderStatus;
import jpaBook.jpaShop.shop.domain.common.Address;
import jpaBook.jpaShop.shop.domain.item.Book;
import jpaBook.jpaShop.shop.exception.NonEnoughStockException;
import jpaBook.jpaShop.shop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EntityManager em;
    // 상품주문
    @Test

    public void orderTest () {
        // given
        Member member = createMember("test User 1");
        em.persist(member);
        Book book = createBook("JPA", 100, 100);
        em.persist(book);
        int orderCount = 12;
        System.out.println("==========test start ======");
        // when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        // then
        Order order =  orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, order.getStatus(), "상품주문시 상태는 ORDER");
        assertEquals(1, order.getOrderItems().size(), "주문한 상품의 종류의 수가 정확해야한다");
        assertEquals(100 - orderCount, book.getStockQuantity(), "주문 수량 만큼 재고가 줄어야한다.");
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        return book;
    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address("seoul", "river", "12423"));
        return member;
    }

    // 주문취소
    @Test
    public void cancelOrderTest () {
        // given
        Member member = createMember("test user");
        Book book = createBook("book name", 10000, 10);
        em.persist(member);
        em.persist(book);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        // then
        orderService.cancelOrder(orderId);
        // when
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "취소상태확인");
        assertEquals(10, book.getStockQuantity(), "취소시 재고 반환 개수 확인");
    }
    @Test
    // 상품주문 재고수량 초과
    public void exceedStockQuantityTest () {
        // given
        Member member = createMember("test User 1");
        Book book = createBook("JPA", 100, 10);
        em.persist(member);
        em.persist(book);
        // when
        final int orderCount = book.getStockQuantity() + 1;
        // then
        assertThrows(NonEnoughStockException.class, () -> {
            orderService.order(member.getId(), book.getId(), orderCount);
        });
    }



}