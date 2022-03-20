package jpaBook.jpaShop.shop.service;

import jpaBook.jpaShop.shop.domain.item.Album;
import jpaBook.jpaShop.shop.domain.item.Book;
import jpaBook.jpaShop.shop.exception.NonEnoughStockException;
import jpaBook.jpaShop.shop.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemServiceTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemService itemService;

    @PersistenceContext
    private EntityManager em;

    // 삼품 재고 보다 많은 수량을 가져오길원할때 exception 확인
    @Test
    public void testExceedQuantity () {
        // Album album = new Album();
        Album album = Album.builder().build();
        album.setName("album test");
        album.setStockQuantity(10);
        final int requestQuantity = 12;
        assertThrows(NonEnoughStockException.class, () -> {
            album.removeStock(requestQuantity);
        });
    }

    @Test
    public void updateTest() {
        Book book = em.find(Book.class, 1L);

        // TX
        book.setName("asdfggsa");


        // 변경감지 == dirty checking , 영속성컨텍스트에 있으면, 자동으로 변경이 감지 됨..

    }

}