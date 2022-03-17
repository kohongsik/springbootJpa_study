package jpaBook.jpaShop.shop.service;

import jpaBook.jpaShop.shop.domain.item.Item;
import jpaBook.jpaShop.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public Long save(Item item) {
        return itemRepository.save(item);
    }

    public Item find(Long itemId) {
        return itemRepository.findOne(itemId);
    }
    public List<Item> findALl () {
        return itemRepository.findAll();
    }
    public List<Item> findALl (int offset, int size) {
        return itemRepository.findAll(offset, size);
    }
}
