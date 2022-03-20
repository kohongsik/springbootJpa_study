package jpaBook.jpaShop.shop.repository;

import jpaBook.jpaShop.shop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public Long save (Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            Item ret = em.merge(item); // update 비슷한것.. / update는 아님
            // item : paramter, merge될 인자
            // ret : 병함 후, 영속선 컨텍스트에서 가져오는 엔티티
            // 모든 필드를 병합하기떄문에 사이드 이펙트가 발생하거나 취약점이 있음
            // form 상에서 금액만 나타나면 다른필드값들이 null로 될 수 가 있음
            // 비추...
        }
        return item.getId();
    }

    public Item findOne(Long itemId) {
        return em.find(Item.class, itemId);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
    public List<Item> findAll(int offset, int size) {
        return em.createQuery("select i from Item i", Item.class)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }

}
