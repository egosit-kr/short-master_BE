package kr.egosit.shortmaster.domain.user;

import jakarta.persistence.*;
import kr.egosit.shortmaster.domain.model.BaseEntity;
import kr.egosit.shortmaster.domain.model.Role;
import lombok.*;

@Table
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;

    private String name;

    private String email;

    private String picture;

    private boolean watermark;
    
    private int token;

    @Enumerated(EnumType.STRING)
    private Role role;
}
