package jpaBook.jpaShop.shop.repository.order.simpleQuery;

import jpaBook.jpaShop.shop.domain.OrderSearch;
import jpaBook.jpaShop.shop.repository.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
/*
* 유지보수성이 필요한 복잡한 쿼리는 따로 관리 ...
* */
public class OrderSimpleQueryRepository {
    private final EntityManager em;
    public List<OrderSimpleQueryDto> findAllByStringDto(OrderSearch orderSearch) {
        // 생성자에 엔티티를 넣을 수 없음 -> jpql상 엔티티는 실별자 값으로 치환됨
        String query = "select new jpaBook.jpaShop.shop.repository.OrderSimpleQueryDto(o.id," +
                "m.name," +
                "o.orderDate," +
                "o.status," +
                "d.address) " +
                "from Order o " +
                "join o.member m " +
                "join o.delivery d ";
        return em.createQuery(query, OrderSimpleQueryDto.class).getResultList();

    }
}
