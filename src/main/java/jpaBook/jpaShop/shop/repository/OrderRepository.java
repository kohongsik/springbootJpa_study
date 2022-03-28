package jpaBook.jpaShop.shop.repository;

import jpaBook.jpaShop.shop.domain.Order;
import jpaBook.jpaShop.shop.domain.OrderSearch;
import jpaBook.jpaShop.shop.repository.order.simpleQuery.OrderSimpleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;
    public Long save (Order order) {
        em.persist(order);
        return order.getId();
    }

    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }

    public List<Order> findAllByString (OrderSearch orderSearch) {
        String query = "select o from Order o join o.member m join fetch o.orderItems";
        boolean isFirstCondition = false;
        if (orderSearch.getOrderStatus() != null) {
            query = query + " where o.status = :status";
            isFirstCondition = true;
        }
        if (!StringUtils.isEmpty(orderSearch.getMemberName())) {
            query = isFirstCondition
                    ? query + " and m.name like :name"
                    : query + " where m.name like :name";
        }
        TypedQuery<Order> typedQuery = em.createQuery(query, Order.class);
        if (orderSearch.getOrderStatus() != null) {
            typedQuery.setParameter("status", orderSearch.getOrderStatus());
        }
        if (!StringUtils.isEmpty(orderSearch.getMemberName())) {
            typedQuery.setParameter("name", "%" + orderSearch.getMemberName() + "%");
        }
        return typedQuery.getResultList();
    }

    public List<OrderSimpleQueryDto> findAllByStringDto(OrderSearch orderSearch) {
        // 생성자에 엔티티를 넣을 수 없음 -> jpql상 엔티티는 실별자 값으로 치환됨
        return orderSimpleQueryRepository.findAllByStringDto(orderSearch);
    }

    public List<Order> findAllByStringWithMemberDelivery(OrderSearch orderSearch) {
        String query = "select o from Order o " +
                "join fetch o.member " +
                "join fetch o.delivery";
        return em.createQuery(query, Order.class).getResultList();
    }
/*
    public List<Order> findAll (OrderSearch orderSearch) {
        return em.createQuery("select o from Order o", Order.class)
                .getResultList();
    }
    public List<Order> findAll (OrderSearch orderSearch, int offset, int size) {
        return em.createQuery("select o from Order o", Order.class)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }

    public List<Order> findAll () {
        return em.createQuery("select o from Order o", Order.class)
                .getResultList();
    }

    public List<Order> findAll (int offset, int size) {
        return em.createQuery("select o from Order o", Order.class)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }
 */
}
