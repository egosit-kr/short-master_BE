package kr.egosit.shortmaster.domain.item;

import jakarta.persistence.*;
import kr.egosit.shortmaster.domain.model.BaseEntity;
import kr.egosit.shortmaster.domain.model.BillingType;
import lombok.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int addToken;
    private boolean watermark;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private BillingType billingType;

}
