package jpaBook.jpaShop.sample.hello;

import jpaBook.jpaShop.shop.domain.*;
import jpaBook.jpaShop.shop.domain.common.Address;
import jpaBook.jpaShop.shop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;
    @PostConstruct
    public void init () {
        initService.InitDb1();
        initService.InitDb2();
    }
    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {
        private final EntityManager em;
        public void InitDb1 () {
            Member member = createMember(new Address("Seoul", "Somewhere", "1324"), "userA");
            em.persist(member);

            Book book1 = getBook("ASD", 10000, 100, "JPA1 BOOK");
            em.persist(book1);

            Book book2 = getBook("DSA", 20000, 100, "JPA2 BOOK");
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 1);
            em.persist(orderItem1);
            em.persist(orderItem2);
            Order order = Order.createOrder(member
                    , Delivery.builder().address(new Address("ASAN", "CH", "415")).status(DeliveryStatus.READY).build()
                    , orderItem1, orderItem2);
            em.persist(order);
        }
        public void InitDb2 () {
            Member member = createMember(new Address("Seoul", "Somewhere", "1324"), "userB");
            em.persist(member);

            Book book1 = getBook("ASD2", 10000, 100, "SPRING1 BOOK");
            em.persist(book1);

            Book book2 = getBook("DSA2", 20000, 100, "SPRING2 BOOK");
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 1);
            em.persist(orderItem1);
            em.persist(orderItem2);
            Order order = Order.createOrder(member
                    , Delivery.builder().address(new Address("ASAN222", "CH32", "415")).status(DeliveryStatus.READY).build()
                    , orderItem1, orderItem2);
            em.persist(order);
        }

        private Book getBook(String author, int price, int stockQuantity, String jpa1_book) {
            Book book1 = Book.builder().author(author).build();
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            book1.setName(jpa1_book);
            return book1;
        }
        private static Member createMember(Address address, String userName) {
            Member member = Member
                    .builder()
                    .name(userName)
                    .address(address)
                    .build();
            return member;
        }

    }

}
