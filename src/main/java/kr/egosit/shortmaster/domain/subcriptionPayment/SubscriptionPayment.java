package kr.egosit.shortmaster.domain.subcriptionPayment;

import jakarta.persistence.*;
import kr.egosit.shortmaster.domain.item.Item;
import kr.egosit.shortmaster.domain.model.BaseEntity;
import kr.egosit.shortmaster.domain.model.Status;
import kr.egosit.shortmaster.domain.user.User;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPayment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    private BigDecimal amount;
    private LocalDate nextBillingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;
}
