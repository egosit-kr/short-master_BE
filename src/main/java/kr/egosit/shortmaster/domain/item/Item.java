package kr.egosit.shortmaster.domain.item;

import jakarta.persistence.*;
import kr.egosit.shortmaster.domain.model.BaseEntity;
import kr.egosit.shortmaster.domain.model.BillingType;
import lombok.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private int addToken;

    @Column
    private boolean watermark;

    @Column
    private BigDecimal price;

    @Column
    private boolean hidden;

    @Column
    @Enumerated(EnumType.STRING)
    private BillingType billingType;

    public void update(String name, String description, Integer addToken, Boolean watermark, BigDecimal price, Boolean hidden, BillingType billingType) {
        if(name != null)
            this.name = name;
        if(description != null)
            this.description = description;
        if(addToken != null)
            this.addToken = addToken;
        if(watermark != null)
            this.watermark = watermark;
        if(price != null)
            this.price = price;
        if(hidden != null)
            this.hidden = hidden;
        if(billingType != null)
            this.billingType = billingType;
    }
}
