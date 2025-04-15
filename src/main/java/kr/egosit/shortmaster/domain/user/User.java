package kr.egosit.shortmaster.domain.user;

import jakarta.persistence.*;
import kr.egosit.shortmaster.domain.model.BaseEntity;
import kr.egosit.shortmaster.domain.model.Role;
import lombok.*;

import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<String> provider;

    private String name;

    private String email;

    private String picture;

    private boolean watermark;
    
    private int token;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User update(String name, String email, String picture, String provider) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        if(this.provider.isEmpty() || !this.provider.contains(provider)) {
            this.provider.add(provider);
        }
        return this;
    }

}
