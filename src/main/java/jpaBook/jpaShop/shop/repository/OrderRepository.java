package jpaBook.jpaShop.shop.repository;

import jpaBook.jpaShop.shop.domain.Order;
import jpaBook.jpaShop.shop.domain.OrderSearch;
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
