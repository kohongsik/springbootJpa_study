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
            em.merge(item); // update 비슷한것.. / update는 아님
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
