package kr.egosit.shortmaster.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Column(nullable = false, updatable = false)
    private LocalDate createAt;

    @Column(nullable = false)
    private LocalDate updateAt;

    @PrePersist
    public void prePersist() {
        LocalDate now = LocalDate.now();
        this.createAt = now;
        this.updateAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updateAt = LocalDate.now();
    }
}
