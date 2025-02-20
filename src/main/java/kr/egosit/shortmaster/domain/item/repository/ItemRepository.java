package kr.egosit.shortmaster.domain.item.repository;

import kr.egosit.shortmaster.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
