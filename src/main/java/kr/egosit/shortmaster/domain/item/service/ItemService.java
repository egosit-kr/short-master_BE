package kr.egosit.shortmaster.domain.item.service;

import kr.egosit.shortmaster.domain.item.Item;
import kr.egosit.shortmaster.domain.item.repository.ItemRepository;
import kr.egosit.shortmaster.domain.model.BillingType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.egosit.shortmaster.domain.item.exception.ItemNotFoundException;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Optional<Item> findById(long id) {
        return itemRepository.findById(id);
    }

    public Item getById(long id) {
        return itemRepository.findById(id).orElseThrow(
                ItemNotFoundException::new
        );
    }

    public Item createItem(String name, String description, int addToken, boolean watermark, BigDecimal price, BillingType billingType) {
        Item item = Item.builder()
                .name(name)
                .description(description)
                .addToken(addToken)
                .watermark(watermark)
                .price(price)
                .billingType(billingType)
                .hidden(false)
                .build();

        return itemRepository.save(item);
    }

    public Item updateItem(long itemId, String name, String description, Integer addToken, Boolean watermark, BigDecimal price, BillingType billingType, Boolean hidden) {
        Item item = getById(itemId);

        item.update(name, description, addToken, watermark, price, hidden, billingType);

        return itemRepository.save(item);
    }
}
